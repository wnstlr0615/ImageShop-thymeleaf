package com.joon.imageshopthymeleaf.service.impl;

import com.joon.imageshopthymeleaf.common.exception.ItemBuyFailException;
import com.joon.imageshopthymeleaf.entity.Item;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.entity.PayCoin;
import com.joon.imageshopthymeleaf.entity.UserItem;
import com.joon.imageshopthymeleaf.repository.ItemRepository;
import com.joon.imageshopthymeleaf.repository.MemberRepository;
import com.joon.imageshopthymeleaf.repository.PayCoinRepository;
import com.joon.imageshopthymeleaf.repository.UserItemRepository;
import com.joon.imageshopthymeleaf.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserItemServiceImpl implements UserItemService {
    private final UserItemRepository userItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final PayCoinRepository coinRepository;
    private final EntityManager entityManager;

    private void createUserItem(Member member, Item item) {
        UserItem userItem = UserItem.builder()
                .member(member)
                .item(item)
                .build();
        userItemRepository.save(userItem);
    }

    @Override
    @Transactional
    public void register(Member member, Long itemId) {
        Item item = itemRepository.findById(itemId).get();
        Member findMember = memberRepository.findById(member.getMemberId()).get();
        if(findMember.buyItem(item)) {
            createUserItem(member, item);
            createPayCoin(member, item);
        }else{
            throw new ItemBuyFailException("buy Item Fail ");
        }
    }

    @Override
    public List<UserItem> list(Member member) {
        return userItemRepository.findByMember(member);
    }

    @Override
    public UserItem findOne(Long userItemId) {
        return userItemRepository.findById(userItemId).get();
    }

    @Override
    public UserItem download(Long userItemNo, Member member) {
        return userItemRepository.findByUserItemIdAndMember(userItemNo, member).get();
    }


    private void createPayCoin(Member member, Item item) {
        PayCoin payCoin = PayCoin.builder()
                .itemId(item.getItemId())
                .memberId(member.getMemberId())
                .itemName(item.getItemName())
                .amount(item.getPrice())
                .build();
        coinRepository.save(payCoin);
    }

}
