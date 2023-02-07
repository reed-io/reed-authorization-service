/**
 * E5Projects @ org.Reed.authorization/AuthorizationServiceConfiguration.java
 */
package org.reed.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author chenxiwen
 * @createTime 2020年03月02日 下午3:21
 * @description
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServiceConfiguration implements AuthorizationServerConfigurer {

    @Autowired
    // @Qualifier("enderPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    @Qualifier("enderAuthorizationCodeServices")
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    @Qualifier("enderClientDetailsService")
    private ClientDetailsService clientDetailsService;

    @Autowired
    @Qualifier("enderAccessTokenConverter")
    private JwtAccessTokenConverter accessTokenConverter;

    /**
     * Configure the security of the Authorization Server, which means in practical terms the
     * /oauth/token endpoint. The /oauth/authorize endpoint also needs to be secure, but that is a
     * normal user-facing endpoint and should be secured the same way as the rest of your UI, so is
     * not covered here. The default settings cover the most common requirements, following
     * recommendations from the OAuth2 spec, so you don't need to do anything here to get a basic
     * server up and running.
     *
     * @param security a fluent configurer for security features
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()") // /oauth/token_key
                .checkTokenAccess("permitAll()") // /oauth/check_token
                .allowFormAuthenticationForClients();
    }

    /**
     * Configure the {@link ClientDetailsService}, e.g. declaring individual clients and their
     * properties. Note that password grant is not enabled (even if some clients are allowed it)
     * unless an {@link AuthenticationManager} is supplied to the
     * {@link #configure(AuthorizationServerEndpointsConfigurer)}. At least one client, or a fully
     * formed custom {@link ClientDetailsService} must be declared or the server will not start.
     *
     * @param clients the client details configurer
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * Configure the non-security features of the Authorization Server endpoints, like token store,
     * token customizations, user approvals and grant types. You shouldn't need to do anything by
     * default, unless you need password grants, in which case you need to provide an
     * {@link AuthenticationManager}.
     *
     * @param endpoints the endpoints configurer
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)// 认证管理器
                .authorizationCodeServices(authorizationCodeServices)// 授权码服务
                .tokenServices(tokenService())// 令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
        endpoints.pathMapping("/oauth/confirm_access", "/auth/confirm_access");
    }



    @Bean("enderAuthorizationCodeServices")
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);// 设置授权码模式的授权码如何存取
    }

    // 令牌管理服务
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService);// 客户端详情服务
        service.setSupportRefreshToken(true);// 支持刷新令牌
        service.setTokenStore(tokenStore);// 令牌存储策略
        // 令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain
                .setTokenEnhancers(Arrays.asList(accessTokenConverter, new ReedTokenEnhancer()));
        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }


    // 将客户端信息存储到数据库
    @Bean("enderClientDetailsService")
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }
}


// http://localhost:8080/oauth/authorize?response_type=code&client_id=client1&scope=scope1&redirect_url=http://www.baidu.com
