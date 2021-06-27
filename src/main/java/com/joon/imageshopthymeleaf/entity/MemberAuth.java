package com.joon.imageshopthymeleaf.entity;

import com.joon.imageshopthymeleaf.common.entity.BaseTimeEntity;
import com.joon.imageshopthymeleaf.common.model.Auth;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAuth extends BaseTimeEntity {
   @Id @GeneratedValue
    private Long memberAuthID;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "memberId")
   private Member member;

   @Enumerated(EnumType.STRING)
   private Auth auth;
}
