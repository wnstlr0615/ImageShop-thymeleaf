package com.joon.imageshopthymeleaf.repository;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootApplication
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Description("list() 테스트")
    public void getList(){
        memberRepository.list();
    }
}