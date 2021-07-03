package com.joon.imageshopthymeleaf.service;

import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.entity.UserItem;

import java.util.List;

public interface UserItemService {
    void register(Member member, Long itemId);
    List<UserItem> list(Member member);

    UserItem findOne(Long userItemId);

    UserItem download(Long userItemNo, Member member);
}
