package com.joon.imageshopthymeleaf.service.impl;

import com.joon.imageshopthymeleaf.entity.Board;
import com.joon.imageshopthymeleaf.repository.BoardRepository;
import com.joon.imageshopthymeleaf.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl  implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void register(Board board) {
        boardRepository.save(board);
    }

    @Override
    public List<Board> list() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardNo"));
    }

    @Override
    public Board read(Long boardNo) {
        return boardRepository.findById(boardNo).get();
    }

    @Override
    @Transactional
    public void remove(Long boardNo) {
        boardRepository.deleteById(boardNo);
    }

    @Override
    @Transactional
    public void modify(Board board) {
        Board findBoard = boardRepository.findById(board.getBoardNo()).get();
        findBoard.update(board.getTitle(), board.getContent());
    }
}
