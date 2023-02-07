/**
 * E5Projects @ org.Reed.authorization/TokenConfiguration.java
 */
package org.reed.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author chenxiwen
 * @createTime 2020年03月03日 下午2:55
 * @description
 */
@Configuration
public class TokenConfiguration {

    private String jwtSignKey = "ender";

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean("enderAccessTokenConverter")
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSignKey);
        return converter;
    }


    // @Bean
    // public TokenEnhancerChain tokenEnhancerChain(){
    // TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    // tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new ReedTokenEnhancer(),
    // accessTokenConverter()));
    // return tokenEnhancerChain;
    // }
}
