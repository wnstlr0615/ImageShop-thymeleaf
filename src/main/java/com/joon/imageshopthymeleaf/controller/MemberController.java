package com.joon.imageshopthymeleaf.controller;

import com.joon.imageshopthymeleaf.common.exception.NotFoundUser;
import com.joon.imageshopthymeleaf.common.model.CodeLabelValue;
import com.joon.imageshopthymeleaf.dto.MemberReadDto;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.service.CodeDetailService;
import com.joon.imageshopthymeleaf.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class MemberController {
    private final CodeDetailService codeDetailService;
    private final MemberService memberService;
    String classCode="A01";
    @GetMapping("/register")
    public String registerForm(Model model){
        Member member = new Member();
        model.addAttribute("jobList", codeDetailService.getCodeList(classCode));
        model.addAttribute("member", member);
        return "/user/register";
    }
    @PostMapping("/register")
    public String register(@Valid Member member, Model model, RedirectAttributes rttr, Errors errors){
        if(errors.hasErrors()){
            model.addAttribute("jobList", codeDetailService.getCodeList(classCode));

            return "/user/register";
        }
        memberService.register(member);
        rttr.addFlashAttribute("userName", member.getUserName());
        return "redirect:/user/registerSuccess";
    }
    @GetMapping("/registerSuccess")
    public String registerSuccess(){
        return "/user/registerSuccess";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String list(Model model){
        model.addAttribute("list", memberService.list());
        return "/user/list";
    }
    @GetMapping("/read")
    public String read(long memberId, Model model){
        MemberReadDto memberReadDto= memberService.read(memberId);
        model.addAttribute("member", memberReadDto);

        model.addAttribute("jobList", codeDetailService.getCodeList(classCode));

        return "/user/read";
    }
    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String remove(long memberId, RedirectAttributes rttr){
        memberService.remove(memberId);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/user/list";
    }
    @ExceptionHandler(NotFoundUser.class)
    public String NotFoundUserExceptionHandler(NotFoundUser e, RedirectAttributes rttr){
        rttr.addFlashAttribute("msg", e.getMessage());
        return "redirect:/user/list";
    }
    @GetMapping("/modify")
    public String modifyForm(long memberId, Model model){
        MemberReadDto memberReadDto = memberService.read(memberId);
        model.addAttribute("member", memberReadDto);
        model.addAttribute("jobList", codeDetailService.getCodeList(classCode));
        return "/user/modify";
    }
    @PostMapping("/modify")
    public String modify(MemberReadDto member, RedirectAttributes rttr){
        memberService.update(member);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/user/list";
    }
    @GetMapping("/setup")
    public String setUpAdminForm(Member member, Model model){
        if(memberService.countAll()==0L){
            return "/user/setup";
        }
        return "/user/setupFailure";
    }
    @PostMapping("/setup")
    public String setupAdmin(Member member, RedirectAttributes rttr){
        if(memberService.countAll()!=0){
            return "/user/setupFailure";
        }
        memberService.setupAdmin(member);
        rttr.addFlashAttribute("userName", member.getUserName());
        return "redirect:/user/registerSuccess";
    }
}
