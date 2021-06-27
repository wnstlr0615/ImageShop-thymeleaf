package com.joon.imageshopthymeleaf.service;

import com.joon.imageshopthymeleaf.dto.MemberListDto;
import com.joon.imageshopthymeleaf.dto.MemberReadDto;
import com.joon.imageshopthymeleaf.entity.Member;

import java.util.List;

public interface MemberService {
    void register(Member member);
    List<MemberListDto> list();

    MemberReadDto read(long memberId);

    void remove(long memberId);

    void update(MemberReadDto memberDto);

    long countAll();

    void setupAdmin(Member member);
}
