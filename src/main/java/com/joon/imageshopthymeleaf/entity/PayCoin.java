package com.joon.imageshopthymeleaf.entity;

import com.joon.imageshopthymeleaf.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PayCoin extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long payCoinId;

    private Long memberId;
    private Long itemId;

    private String itemName;
    private int amount;
}
