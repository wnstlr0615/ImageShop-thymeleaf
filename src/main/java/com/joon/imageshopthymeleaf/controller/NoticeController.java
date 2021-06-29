package com.joon.imageshopthymeleaf.controller;

import com.joon.imageshopthymeleaf.entity.Notice;
import com.joon.imageshopthymeleaf.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("notice", new Notice());
        return "/notice/register";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String register(@Valid Notice notice, RedirectAttributes redirectAttributes){ //TODO 에러처리
        log.info(notice.toString());
        noticeService.register(notice);
        //공지 생성 완료
        redirectAttributes.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/notice/list";
    }

    @GetMapping("/list")
    public String list(Model model){
        List<Notice> noticeList=  noticeService.list();
        model.addAttribute("noticeList", noticeList);
        return "/notice/list";
    }
    @GetMapping("/read")
    public String read(Long noticeId, Model model){
        Notice notice=noticeService.read(noticeId);
        model.addAttribute("notice", notice);
        return "/notice/read";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modify")
    public String modifyForm(Long noticeId, Model model){
        Notice notice=noticeService.read(noticeId);
        model.addAttribute("notice", notice);
        return "/notice/modify";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modify")
    public String modify(Notice notice, RedirectAttributes rttr){
        noticeService.modify(notice);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/notice/list";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/remove")
    public String remove(Long noticeId, RedirectAttributes rttr){
        noticeService.remove(noticeId);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/notice/list";
    }
}
