package com.joon.imageshopthymeleaf.entity;

import com.joon.imageshopthymeleaf.common.entity.BaseTimeEntity;
import com.joon.imageshopthymeleaf.dto.MemberReadDto;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode()
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue
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

    public void passWdEncrypt(BCryptPasswordEncoder encoder) {
        this.userPw=encoder.encode(userPw);
    }

    public void updateInfo(String userName, String job) {
        this.userName=userName;
        this.job=job;
    }

    public void addCoin(int amount) {
        this.coin+=amount;
    }

    public boolean buyItem(Item item) {
        if(item.getPrice()>this.coin) {
            return false;
        }
        this.coin-= item.getPrice();
        return true;
    }
}

