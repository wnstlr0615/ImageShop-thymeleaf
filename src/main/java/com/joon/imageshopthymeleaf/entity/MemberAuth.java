package com.joon.imageshopthymeleaf.entity;

import com.joon.imageshopthymeleaf.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAuth extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long memberAuthId;

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
}
