package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    List<UserItem> findByMember(Member member);

    Optional<UserItem> findByUserItemIdAndMember(Long userItemNo, Member member);
}
