/**
 * E5Projects @ org.reed.security/reedServiceSecurityConfiguration.java
 */
package org.reed.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author chenxiwen
 * @createTime 2020年03月03日 下午10:19
 * @description
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ReedServiceSecurityConfiguration extends WebSecurityConfigurerAdapter {
    // @Autowired
    // @Qualifier("reedUserDetailsService")
    // private UserDetailsService userDetailsService;

    // 认证管理器
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                // .antMatchers("/actuator/**").permitAll()
                .antMatchers("/login*").permitAll()
                // .antMatchers("/auth/login", "/login", "/oauth/authorize")
                .anyRequest().permitAll().and().formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/login").and().logout();
    }

    // @Bean("enderPasswordEncoder")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws
    // Exception {
    // authenticationManagerBuilder
    // .userDetailsService(userDetailsService)
    // .passwordEncoder(passwordEncoder());
    // }


    // @Bean("reedAuthenticationManager")
    // @Override
    // public AuthenticationManager authenticationManager() throws Exception {
    // return super.authenticationManagerBean();
    // }
}
