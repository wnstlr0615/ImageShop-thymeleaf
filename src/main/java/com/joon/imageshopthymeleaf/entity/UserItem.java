package com.joon.imageshopthymeleaf.entity;

import com.joon.imageshopthymeleaf.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserItem extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long userItemId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
