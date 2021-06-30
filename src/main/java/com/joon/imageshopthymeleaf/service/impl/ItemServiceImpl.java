package com.joon.imageshopthymeleaf.service.impl;

import com.joon.imageshopthymeleaf.entity.Item;
import com.joon.imageshopthymeleaf.repository.ItemRepository;
import com.joon.imageshopthymeleaf.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void register(Item item) {
        itemRepository.save(item);
    }

    @Override
    public List<Item> list() {
        return itemRepository.findAll(Sort.by(Sort.Direction.DESC, "itemId"));
    }

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).get();
    }

    @Override
    public String getPreview(Long itemId) {
        return itemRepository.findById(itemId).get().getPreviewUrl();
    }

    @Override
    @Transactional
    public void remove(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    @Transactional
    public void modify(Item item) {
        Item itemEntity = itemRepository.findById(item.getItemId()).get();
        itemEntity.update(item.getItemName(), item.getPrice(), item.getDescription(), item.getPictureUrl(), item.getPreviewUrl());
    }
}
