package com.joon.imageshopthymeleaf.controller;

import com.joon.imageshopthymeleaf.common.exception.NotFoundGroupCodeException;
import com.joon.imageshopthymeleaf.common.model.CodeLabelValue;
import com.joon.imageshopthymeleaf.dto.CodeDetailDto;
import com.joon.imageshopthymeleaf.entity.CodeDetail;
import com.joon.imageshopthymeleaf.service.CodeDetailService;
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
 * 코드 그룹 컨트롤러
 * */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/codeDetail")
@Log
public class CodeDetailController {
    private final CodeDetailService codeDetailService;
    private final CodeGroupService codeGroupService;
/**
 *   코드 등록 화면 요청
 * */
    @GetMapping("/register")
    public String registerForm(Model model){
        CodeDetailDto codeDetail = new CodeDetailDto();
        model.addAttribute("codeDetail", codeDetail);

        List<CodeLabelValue> groupCodeList=codeGroupService.getCodeGroupList();
        model.addAttribute("groupCodeList", groupCodeList);
        return "/codeDetail/register";
    }
    /**
     *   코드 등록 요청
     * */
    @PostMapping("/register") //
    public String register(CodeDetailDto codeDetail, RedirectAttributes rttr){
        System.out.println(codeDetail);
        codeDetailService.register(codeDetail);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/codeDetail/list";
    }
    /**
     *   코드 리스트 요청
     * */
    @GetMapping("/list")
    public String list(Model model){
        List<CodeDetail> codeDetailList=codeDetailService.list();
        model.addAttribute("list", codeDetailList);
        return "/codeDetail/list";
    }
    /**
     *   코드 상세보기 요청
     * */
    @GetMapping("/read")
    public String read(String groupCode, String codeValue, Model  model){
        CodeDetailDto codeDetailDto = codeDetailService.findByGroupAndCodeValue(groupCode, codeValue);
        model.addAttribute("codeDetail", codeDetailDto);

        List<CodeLabelValue> groupCodeList=codeGroupService.getCodeGroupList();
        model.addAttribute("groupCodeList", groupCodeList);
        return "/codeDetail/read";
    }
    /**
     *   코드 삭제 요청
     * */
    @PostMapping("/remove")
    public String remove(CodeDetailDto codeDetail, RedirectAttributes rttr){
        codeDetailService.remove(codeDetail.getGroupCode(), codeDetail.getCodeValue());
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/codeDetail/list";
    }
    /**
     *   코드 수정 화면 요청
     * */
    @GetMapping("/modify")
    public String modifyForm(String groupCode, String codeValue, Model model){
        CodeDetailDto codeDetailDto = codeDetailService.findByGroupAndCodeValue(groupCode, codeValue);
        model.addAttribute("codeDetail", codeDetailDto);

        List<CodeLabelValue> groupCodeList=codeGroupService.getCodeGroupList();
        model.addAttribute("groupCodeList", groupCodeList);
        return "/codeDetail/modify";
    }
    /**
     * 코드 수정 요청
     * */
    @PostMapping("/modify")
    public String modify(CodeDetailDto codeDetail, RedirectAttributes rttr){
        codeDetailService.update(codeDetail);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/codeDetail/list";
    }
    /**
     * 예외 처리
     * */
    @ExceptionHandler(NotFoundGroupCodeException.class)
    public String NotFoundExceptionHandler(NotFoundGroupCodeException e, RedirectAttributes rttr ){
        rttr.addFlashAttribute("msg", e.getMessage());
        return "redirect:/codeDetail/list";
    }
}
