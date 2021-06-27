package com.joon.imageshopthymeleaf.config;

import com.joon.imageshopthymeleaf.common.security.CustomAccessDeniedHandler;
import com.joon.imageshopthymeleaf.common.security.CustomLoginSuccessHandler;
import com.joon.imageshopthymeleaf.common.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class securityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/user/register", "/user/registerSuccess").permitAll()
                .antMatchers("/user/setup").permitAll()
                .antMatchers("/user/**").hasRole("ADMIN")
                .antMatchers("/codeGroup/**").hasRole("ADMIN")
                .antMatchers("/codeDetail/**").hasRole("ADMIN")
                .antMatchers("/board/list", "/board/read").permitAll()
                .antMatchers("/board/remove").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/board/**").hasRole("MEMBER")
                .anyRequest().authenticated();



        http.formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .successHandler(createAuthenticationSuccessHandler());
        http.exceptionHandling()
                .accessDeniedHandler(createAccessDeniedHandler());
        http.rememberMe()
                .key("joon")
                .tokenRepository(createJDBCRepository())
                .tokenValiditySeconds(60*60*24);
        http.logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("remember-me", "JSESSION_ID");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(createUserDetailsService())
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
    @Bean
    public UserDetailsService createUserDetailsService() {
        return new CustomUserDetailsService();
    }

}
