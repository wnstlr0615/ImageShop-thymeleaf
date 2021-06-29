package com.joon.imageshopthymeleaf.controller;

import com.joon.imageshopthymeleaf.common.model.CodeLabelValue;
import com.joon.imageshopthymeleaf.common.security.domain.CustomUser;
import com.joon.imageshopthymeleaf.controller.vo.PageRequestVO;
import com.joon.imageshopthymeleaf.dto.PaginationDTO;
import com.joon.imageshopthymeleaf.entity.Board;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/register")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String registerForm(Model model, Authentication authentication){
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();
        Board board = Board.builder()
                .writer(member.getUserName()).build();
        model.addAttribute("board", board);
        return "/board/register";
    }
    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String register(Board board, RedirectAttributes rttr) throws Exception {
        boardService.register(board);

        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(@ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception {
        Page<Board> page=boardService.list(pageRequestVO);
        List<CodeLabelValue> searchTypeCodeValueList =new ArrayList<>();
        searchTypeCodeValueList.add(new CodeLabelValue("n", "---"));
        searchTypeCodeValueList.add(new CodeLabelValue("t", "Title"));
        searchTypeCodeValueList.add(new CodeLabelValue("c", "Content"));
        searchTypeCodeValueList.add(new CodeLabelValue("w", "Writer"));
        searchTypeCodeValueList.add(new CodeLabelValue("tc", "Title OR Content"));
        searchTypeCodeValueList.add(new CodeLabelValue("cw", "Content OR Writer"));
        searchTypeCodeValueList.add(new CodeLabelValue("tcw", "Title OR Content OR Writer"));


        model.addAttribute("pgntn", new PaginationDTO<Board>(page));
        model.addAttribute("searchTypeCodeValueList", searchTypeCodeValueList);
        return "/board/list";
    }

    @GetMapping("/read")
    public String read(Long boardNo, Model model, @ModelAttribute("pgrq")PageRequestVO pageRequestVO) throws Exception {
        model.addAttribute("board", boardService.read(boardNo));
        return "/board/read";
    }

    @PostMapping("/remove")
    @PreAuthorize("(hasRole('ROLE_MEMBER') and principal.username == #board.writer) or hasRole('ROLE_ADMIN')")
    public String remove(Long boardNo, RedirectAttributes rttr, PageRequestVO pageRequestVO, Board board) throws Exception {
        boardService.remove(boardNo);
        rttr.addAttribute("page", pageRequestVO.getPage());
        rttr.addAttribute("sizePerPage", pageRequestVO.getSizePerPage());
        rttr.addAttribute("keyword", pageRequestVO.getKeyword());
        rttr.addAttribute("searchType", pageRequestVO.getSearchType());
        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/board/list";
    }

    @GetMapping( "/modify")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String modifyForm(Long boardNo, Model model, @ModelAttribute("pgrq")PageRequestVO pageRequestVO) throws Exception {
        model.addAttribute("board", boardService.read(boardNo));
        return "/board/modify";
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username == #board.writer")
    public String modify(Board board, RedirectAttributes rttr, @ModelAttribute("pgrq")PageRequestVO pageRequestVO) throws Exception {
        boardService.modify(board);
        rttr.addAttribute("page", pageRequestVO.getPage());
        rttr.addAttribute("sizePerPage", pageRequestVO.getSizePerPage());
        rttr.addAttribute("keyword", pageRequestVO.getKeyword());
        rttr.addAttribute("searchType", pageRequestVO.getSearchType());
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/board/list";
    }
}
