package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.entity.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAuthRepository extends JpaRepository<MemberAuth, Long> {
    List<MemberAuth> findAllByMember(Member findMember);
    void deleteAllByMember(Member findMember);
}
