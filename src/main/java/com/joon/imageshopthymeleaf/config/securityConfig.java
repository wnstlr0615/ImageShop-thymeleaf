package com.joon.imageshopthymeleaf.config;

import com.joon.imageshopthymeleaf.common.security.CustomAccessDeniedHandler;
import com.joon.imageshopthymeleaf.common.security.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class securityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("login")
                .successHandler(createAuthenticationSuccessHandler());
        http.exceptionHandling()
                .accessDeniedHandler(createAccessDeniedHandler());
        http.rememberMe()
                .key("joon")
                .tokenRepository(createJDBCRepository())
                .tokenValiditySeconds(60*60*24);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }
    private PersistentTokenRepository createJDBCRepository(){
        JdbcTokenRepositoryImpl repo =new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);;
        return repo;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationSuccessHandler createAuthenticationSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }
    @Bean
    public AccessDeniedHandler createAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


}
