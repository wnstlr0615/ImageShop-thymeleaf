package com.joon.imageshopthymeleaf.service.impl;

import com.joon.imageshopthymeleaf.controller.vo.PageRequestVO;
import com.joon.imageshopthymeleaf.entity.Board;
import com.joon.imageshopthymeleaf.repository.BoardRepository;
import com.joon.imageshopthymeleaf.repository.CustomBoardRepository;
import com.joon.imageshopthymeleaf.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl  implements BoardService {

    private final BoardRepository boardRepository;
    private final CustomBoardRepository customBoardRepository;
    @Override
    @Transactional
    public void register(Board board) {
        boardRepository.save(board);
    }

    @Override
    public Page<Board> list(PageRequestVO pageRequestVO) {
      /*  int pageNumber=pageRequestVO.getPage()-1;
        int sizePerPage=pageRequestVO.getSizePerPage();
        Pageable pageRequest= PageRequest.of(pageNumber, sizePerPage, Sort.Direction.DESC, "boardNo");
        return boardRepository.findAll(pageRequest);*/
        String searchType= pageRequestVO.getSearchType();
        String keyword = pageRequestVO.getKeyword();
        int pageNumber=pageRequestVO.getPage()-1;
        int sizePerPage= pageRequestVO.getSizePerPage();
        PageRequest pageRequest=PageRequest.of(pageNumber, sizePerPage, Sort.Direction.DESC, "boardNo");
        return customBoardRepository.getSearchPage(searchType, keyword, pageRequest);
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
