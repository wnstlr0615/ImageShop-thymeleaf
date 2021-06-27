package com.joon.imageshopthymeleaf.controller;

import com.joon.imageshopthymeleaf.common.security.domain.CustomUser;
import com.joon.imageshopthymeleaf.entity.Board;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/register")
    @PreAuthorize("hasRole('MEMBER')")
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
    public String list(Model model) throws Exception {
        model.addAttribute("list", boardService.list());
        return "/board/list";
    }

    @GetMapping("/read")
    public String read(Long boardNo, Model model) throws Exception {
        model.addAttribute("board", boardService.read(boardNo));
        return "/board/read";
    }

    @PostMapping("/remove")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
    public String remove(Long boardNo, RedirectAttributes rttr) throws Exception {
        boardService.remove(boardNo);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/board/list";
    }

    @GetMapping( "/modify")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
    public String modifyForm(Long boardNo, Model model) throws Exception {
        model.addAttribute("board", boardService.read(boardNo));
        return "/board/modify";
    }

    @PostMapping("/modify")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
    public String modify(Board board, RedirectAttributes rttr) throws Exception {
        boardService.modify(board);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/board/list";
    }
}
