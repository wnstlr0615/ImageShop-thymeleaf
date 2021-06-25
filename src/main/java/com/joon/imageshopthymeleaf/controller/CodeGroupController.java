package com.joon.imageshopthymeleaf.controller;

import com.joon.imageshopthymeleaf.common.exception.NotFoundGroupCodeException;
import com.joon.imageshopthymeleaf.entity.CodeGroup;
import com.joon.imageshopthymeleaf.service.CodeGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
/**
 * 코드 컨트롤러
 * */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/codeGroup")
@Log
public class CodeGroupController {
    private final CodeGroupService codeGroupService;
    /**
     * 등록 화면 요청
    * */
    @GetMapping("/register")
    public String registerForm(Model model){
        CodeGroup codeGroup = new CodeGroup();
        model.addAttribute("codeGroup", codeGroup);
        return "/codeGroup/register";
    }
    /**
     * 등록 화면 요청
     * */
    @PostMapping("/register")
    public String register(CodeGroup codeGroup, RedirectAttributes rttr){
        codeGroupService.register(codeGroup);
        rttr.addFlashAttribute("msg", "SUCCESS");
        log.info("SUCCESS");
        return "redirect:/codeGroup/list";
    }
    /**
     * 코드 목록 요청
     * */
    @GetMapping("/list")
    public String list(Model model){
        List<CodeGroup> codeGroups = codeGroupService.list();
        model.addAttribute("list", codeGroups);
        return "/codeGroup/list";
    }
    /**
     * 코드 상세보기 요청
     * */
    @GetMapping("/read")
    public String read(String groupCode, Model model){
        model.addAttribute("codeGroup", codeGroupService.getGroupCode(groupCode));
        return "/codeGroup/read";
    }
    /**
     * 코드 삭제 요청
     * */
    @PostMapping("/remove")
    public String remove(String groupCode, RedirectAttributes rttr){
        codeGroupService.remove(groupCode);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/codeGroup/list";
    }
    /**
     * 코드 수정 화면 요청
     * */
    @GetMapping("/modify")
    public String modifyForm(String groupCode, Model model){
        CodeGroup groupCodeEntity = codeGroupService.getGroupCode(groupCode);
        model.addAttribute("codeGroup", groupCodeEntity);
        return "/codeGroup/modify";
    }
    /**
     * 코드 수정 요청
     * */
    @PostMapping("/modify")
    public String modify(CodeGroup codeGroup, RedirectAttributes rttr){
        codeGroupService.update(codeGroup);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/codeGroup/list";
    }
    /**
     * 예외 처리
     * */
    @ExceptionHandler(NotFoundGroupCodeException.class)
    public String NotFoundExceptionHandler(NotFoundGroupCodeException e, RedirectAttributes rttr ){
        rttr.addFlashAttribute("msg", e.getMessage());
        return "redirect:/codeGroup/list";
    }
}
