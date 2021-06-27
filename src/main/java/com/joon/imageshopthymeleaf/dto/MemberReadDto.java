package com.joon.imageshopthymeleaf.dto;

import com.joon.imageshopthymeleaf.common.model.Auth;
import com.joon.imageshopthymeleaf.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberReadDto {
    private Long memberId;

    private String userId;

    private String userPw;

    private String userName;

    private String job;

    private List<String> authList=new ArrayList<>();

    public MemberReadDto(Long memberId, String userId, String userPw, String userName, String job) {
        this.memberId = memberId;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.job = job;
    }

    public static MemberReadDto of(Member member) {
        return MemberReadDto.builder()
                .memberId(member.getMemberId())
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .userName(member.getUserName())
                .job(member.getJob())
                .build();
    }

    public void addAuths(List<String> auths) {
        int authsSize=auths.size();
        for (int i = 0; i <3 ; i++) {
            if(i<authsSize){
                authList.add(auths.get(i));
            }else{
                authList.add("");
            }
        }
    }
}
