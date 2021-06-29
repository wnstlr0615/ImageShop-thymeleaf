package com.joon.imageshopthymeleaf.service;

import com.joon.imageshopthymeleaf.controller.vo.PageRequestVO;
import com.joon.imageshopthymeleaf.entity.Board;
import org.springframework.data.domain.Page;

public interface BoardService {
    void register(Board board);

    Page<Board> list(PageRequestVO pageRequestVO);

    Board read(Long boardNo);

    void remove(Long boardNo);

    void modify(Board board);
}
