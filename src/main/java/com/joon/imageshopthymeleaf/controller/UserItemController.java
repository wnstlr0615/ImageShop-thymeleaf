package com.joon.imageshopthymeleaf.controller;

import com.joon.imageshopthymeleaf.common.security.domain.CustomUser;
import com.joon.imageshopthymeleaf.entity.Member;
import com.joon.imageshopthymeleaf.entity.UserItem;
import com.joon.imageshopthymeleaf.service.UserItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@RequestMapping("/useritem")
@Slf4j
public class UserItemController {
    private final UserItemService userItemService;
    @Value("${upload.path}")
    private String uploadPath;
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public String list(Model model, Authentication authentication){
        Member member = ((CustomUser) authentication.getPrincipal()).getMember();
        model.addAttribute("list", userItemService.list((member)));
        return "/useritem/list";
    }
    @GetMapping("/read")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public String read(Model model, Long userItemId){
        log.info(userItemId+"조회");
        model.addAttribute("userItem", userItemService.findOne(userItemId));
        return "/useritem/read";
    }
    @ResponseBody
    @RequestMapping("/download")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
    public ResponseEntity<byte[]> download(Long userItemId, Authentication authentication) throws Exception {
        Member member = ((CustomUser) authentication.getPrincipal()).getMember();

        UserItem userItem = userItemService.download(userItemId, member);

        String fullName = userItem.getItem().getPictureUrl();

        InputStream in = null;
        ResponseEntity<byte[]> entity = null;

        try {
            HttpHeaders headers = new HttpHeaders();

            in = new FileInputStream(uploadPath + File.separator + fullName);

            String fileName = fullName.substring(fullName.indexOf("_") + 1);

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } finally {
            in.close();
        }

        return entity;
    }
}
