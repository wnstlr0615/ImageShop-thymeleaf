package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.ChargeCoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinRepository extends JpaRepository<ChargeCoin, Long> {
    List<ChargeCoin> findAllByMemberId(Long memberId);
}
