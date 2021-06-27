package com.joon.imageshopthymeleaf.service.impl;

import com.joon.imageshopthymeleaf.common.exception.NotFoundUser;
import com.joon.imageshopthymeleaf.common.model.Auth;
import com.joon.imageshopthymeleaf.dto.MemberListDto;
import com.joon.imageshopthymeleaf.dto.MemberReadDto;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.entity.MemberAuth;
import com.joon.imageshopthymeleaf.repository.MemberAuthRepository;
import com.joon.imageshopthymeleaf.repository.MemberRepository;
import com.joon.imageshopthymeleaf.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberAuthRepository memberAuthRepository;
    private final BCryptPasswordEncoder encoder;


    @Override
    @Transactional
    public void register(Member member) {
        member.passWdEncrypt(encoder);
        Member saveMember = memberRepository.save(member);
        MemberAuth memberAuth = createMemberAuth(saveMember, Auth.ROLE_MEMBER);
        memberAuthRepository.save(memberAuth);
    }

    private MemberAuth createMemberAuth(Member member, Auth auth) {
        return MemberAuth.builder()
                .member(member)
                .auth(auth)
                .build();
    }

    @Override
    public List<MemberListDto> list() {
        return memberRepository.list();
    }

    @Override
    public MemberReadDto read(long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundUser("해당 유저는 찾을 수 없습니다"));
        MemberReadDto memberReadDto = memberRepository.findMemberReadDtoById(memberId).orElseThrow(() -> new NotFoundUser("해당 유저는 찾을 수 없습니다"));
        List<MemberAuth> memberAuths = memberAuthRepository.findAllByMember(findMember);
        List<String> auths = memberAuths.stream().map(a->new String(String.valueOf(a.getAuth()))).collect(Collectors.toList());
        memberReadDto.addAuths(auths);
        //TODO member를 두번 조회 최적화 필요
        return memberReadDto;
    }

    @Override
    @Transactional
    public void remove(long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundUser("해당 유저는 찾을 수 없습니다"));
        memberAuthRepository.deleteAllByMember(findMember);
        memberRepository.delete(findMember);
    }

    @Override
    @Transactional
    public void update(MemberReadDto memberDto) {
        Member findMember = memberRepository.findById(memberDto.getMemberId()).orElseThrow(() -> new NotFoundUser("해당 유저는 찾을 수 없습니다"));
        findMember.updateInfo(memberDto.getUserName(), memberDto.getJob());
        memberAuthRepository.deleteAllByMember(findMember);
        memberDto.getAuthList().stream()
                .distinct()
                .filter(a->!a.equals(""))
                .forEach(auth->{
            MemberAuth memberAuth = createMemberAuth(findMember, Auth.valueOf(auth));
            memberAuthRepository.save(memberAuth);
                });
            }

    @Override
    public long countAll() {
        return memberRepository.count();
    }

    @Override
    @Transactional
    public void setupAdmin(Member member) {
        member.passWdEncrypt(encoder);
        member.setJob("A11");//임시 저장
        MemberAuth memberAuth = createMemberAuth(member, Auth.ROLE_ADMIN);
        memberAuthRepository.save(memberAuth);
        memberRepository.save(member);
    }

}
