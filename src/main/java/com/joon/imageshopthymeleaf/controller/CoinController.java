package com.joon.imageshopthymeleaf.controller;

import com.joon.imageshopthymeleaf.common.security.domain.CustomUser;
import com.joon.imageshopthymeleaf.entity.ChargeCoin;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.entity.PayCoin;
import com.joon.imageshopthymeleaf.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coin")
public class CoinController {
    private final CoinService coinService;
    private final MessageSource messageSource;

    @GetMapping("/charge")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String chargeForm(Model model){
        ChargeCoin chargeCoin=ChargeCoin.builder()
                .amount(1000)
                .build();
        model.addAttribute("chargeCoin", chargeCoin);
        return "/coin/charge";
    }

    @PostMapping("/charge")
    public String charge(int amount, RedirectAttributes rttr, Authentication authentication){
        CustomUser customUser =(CustomUser) authentication.getPrincipal();
        Member member=customUser.getMember();
        coinService.register(member.getMemberId(), amount);
        String message = messageSource.getMessage("coin.chargingComplete", null, Locale.KOREAN);
        rttr.addFlashAttribute("msg", message);
        return "redirect:/coin/success";
    }
    @GetMapping("/success")
    public String success() throws Exception {
        return "/coin/success";
    }
    @GetMapping("/list")
    @PreAuthorize("hasRole('MEMBER')")
    public String list(Model model, Authentication authentication){
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        List<ChargeCoin> chargeCoinList =coinService.list(customUser.getMember());
        model.addAttribute("list", chargeCoinList);
        return "/coin/list";
    }
    @GetMapping("/listPay")
    public String listPayHistory(Model model, Authentication authentication){
        Member member = ((CustomUser) authentication.getPrincipal()).getMember();
        List<PayCoin> payCoins = coinService.listPayHistory(member);
        model.addAttribute("list", payCoins);
        return "/coin/listPay";
    }
}
