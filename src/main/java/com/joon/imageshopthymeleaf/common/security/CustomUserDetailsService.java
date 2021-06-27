package com.joon.imageshopthymeleaf.common.security;

import com.joon.imageshopthymeleaf.common.security.domain.CustomUser;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.repository.MemberAuthRepository;
import com.joon.imageshopthymeleaf.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberAuthRepository memberAuthRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username).get();
        log.info("member : "+member);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = memberAuthRepository.findAllByMember(member).stream().map(auth -> new SimpleGrantedAuthority(auth.getAuth().name())).collect(Collectors.toList());
        return member==null?null:new CustomUser(member, simpleGrantedAuthorities);
    }
}
