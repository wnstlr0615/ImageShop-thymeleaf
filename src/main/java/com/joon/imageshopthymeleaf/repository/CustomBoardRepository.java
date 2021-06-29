package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBoardRepository {
    Page<Board>  getSearchPage(String type, String keyword, Pageable pageable);
}
