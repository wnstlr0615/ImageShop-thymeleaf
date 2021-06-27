package com.joon.imageshopthymeleaf.common.security.domain;

import com.joon.imageshopthymeleaf.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class CustomUser extends User {
    private static final long serialVersionUID=1L;
    private Member member;
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    public CustomUser(Member member, List<SimpleGrantedAuthority> authorities){
        super(member.getUserName(), member.getUserPw(), authorities);
        this.member=member;
    }
    public Member getMember(){
        return member;
    }
}
