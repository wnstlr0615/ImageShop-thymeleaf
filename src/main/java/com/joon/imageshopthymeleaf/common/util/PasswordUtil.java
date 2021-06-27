package com.joon.imageshopthymeleaf.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordUtil {
    private final PasswordEncoder encoder;
    public String encryptPassword(String inputPassword){
        return encoder.encode(inputPassword);
    }
}
