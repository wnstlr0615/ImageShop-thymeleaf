package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
