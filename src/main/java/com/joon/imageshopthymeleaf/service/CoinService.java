package com.joon.imageshopthymeleaf.service;

import com.joon.imageshopthymeleaf.entity.ChargeCoin;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.entity.PayCoin;

import java.util.List;

public interface CoinService {
    void register(Long memberId, int amount);

    List<ChargeCoin> list(Member member);

    List<PayCoin> listPayHistory(Member member);
}
