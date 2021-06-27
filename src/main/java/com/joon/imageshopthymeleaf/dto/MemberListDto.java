package com.joon.imageshopthymeleaf.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@Data
public class MemberListDto {
    private Long memberId;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String userId;

    @Column(length = 200,  nullable = false)
    private String userPw;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String userName;

    @Column(length = 3, nullable = false)
    private String job;

    private int coin;

    private LocalDateTime createDate;

    public MemberListDto(Long memberId, String userId, String userPw, String userName, String job, int coin, LocalDateTime createDate) {
        this.memberId = memberId;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.job = job;
        this.coin = coin;
        this.createDate = createDate;
    }
}
