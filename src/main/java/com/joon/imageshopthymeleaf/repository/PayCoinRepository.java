package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.PayCoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayCoinRepository extends JpaRepository<PayCoin,Long> {
    List<PayCoin> findAllByMemberId(Long memberId);
}
