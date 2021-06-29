package com.joon.imageshopthymeleaf;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;

@SpringBootApplication
@EnableJpaAuditing
public class ImageShopThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageShopThymeleafApplication.class, args);
    }
    @Bean
    JPAQueryFactory JPAQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
