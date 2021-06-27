package com.joon.imageshopthymeleaf.service;

import com.joon.imageshopthymeleaf.entity.Board;

import java.util.List;

public interface BoardService {
    void register(Board board);

    List<Board> list();

    Board read(Long boardNo);

    void remove(Long boardNo);

    void modify(Board board);
}
