package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.dto.MemberListDto;
import com.joon.imageshopthymeleaf.dto.MemberReadDto;
import com.joon.imageshopthymeleaf.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT new com.joon.imageshopthymeleaf.dto.MemberListDto(" +
            "m.memberId, m.userId, m.userPw, m.userName, cd.codeName ,m.coin, m.createDate" +
            ") FROM Member as m" +
            " inner join CodeDetail as cd on m.job=cd.codeValue")
    List<MemberListDto> list();

    @Query("SELECT new com.joon.imageshopthymeleaf.dto.MemberReadDto(" +
            "m.memberId, m.userId, m.userPw, m.userName, cd.codeName) FROM Member as m" +
            " inner join CodeDetail as cd on m.job=cd.codeValue" +
            " where m.memberId= :memberId")
    Optional<MemberReadDto> findMemberReadDtoById(@Param("memberId") Long memberId);

    Optional<Member> findByUserId(String username);
}
