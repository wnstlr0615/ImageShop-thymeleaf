package com.joon.imageshopthymeleaf.service.impl;

import com.joon.imageshopthymeleaf.entity.ChargeCoin;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.repository.CoinRepository;
import com.joon.imageshopthymeleaf.repository.MemberRepository;
import com.joon.imageshopthymeleaf.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoinServiceImpl implements CoinService {
    private final CoinRepository coinRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void register(Long memberId, int amount) {
        ChargeCoin chargeCoin = ChargeCoin.builder()
                .memberId(memberId)
                .amount(amount)
                .build();
        Member member = memberRepository.findById(memberId).get();
        member.addCoin(amount);
        coinRepository.save(chargeCoin);
    }

    @Override
    public List<ChargeCoin> list(Member member) {
        return coinRepository.findAllByMemberId(member.getMemberId());
    }
}
