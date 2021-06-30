package com.joon.imageshopthymeleaf.service;

import com.joon.imageshopthymeleaf.entity.Item;

public interface ItemService {
    void register(Item item);

    Object list();

    Item getItem(Long itemId);

    String getPreview(Long itemId);

    void remove(Long itemId);

    void modify(Item item);
}
