package com.joon.imageshopthymeleaf.controller;

import com.joon.imageshopthymeleaf.entity.Item;
import com.joon.imageshopthymeleaf.service.ItemService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
@Slf4j
public class ItemController {
    private final ItemService itemService;
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String registerForm(Model model) {
        model.addAttribute("item", new Item());
        return "/item/register";
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String register(@Valid Item item, RedirectAttributes rttr) throws IOException {
        MultipartFile pictureFile=item.getPicture();
        MultipartFile previewFile=item.getPreview();
        String createdPictureFilename=uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
        String createdPreviewFilename=uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());
        item.setPictureUrl(createdPictureFilename);
        item.setPreviewUrl(createdPreviewFilename);
        itemService.register(item);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/item/list";

    }
    private String uploadFile(String originalFilename, byte[] fileDate) throws IOException {
        UUID uid=UUID.randomUUID();
        String createdFileName=uid+"_"+originalFilename;
        File target=new File(uploadPath, createdFileName);
        if(!target.exists()){
            target.createNewFile();
        }
        FileCopyUtils.copy(fileDate, target);
        return createdFileName;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public String list(Model  model){
        model.addAttribute("itemList", itemService.list());
        return "/item/list";

    }
    @GetMapping("/read")
    public String read(Long itemId, Model model){
        Item item = itemService.getItem(itemId);
        model.addAttribute("item", item);
        return "/item/read";
    }

    @GetMapping("/modify")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String modifyForm(Long itemId, Model model){
        Item item = itemService.getItem(itemId);
        model.addAttribute("item", item);
        return "/item/modify";
    }
    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> displayFile(Long itemId) throws IOException {
        InputStream in = null;
        ResponseEntity<byte[]> entity = null;

        String fileName = itemService.getPreview(itemId);

        try {
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

            MediaType mType = getMediaType(formatName);

            HttpHeaders headers = new HttpHeaders();

            in = new FileInputStream(uploadPath + File.separator + fileName);

            if (mType != null) {
                headers.setContentType(mType);
            }

            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } finally {
            in.close();
        }
        return entity;
    }
    @GetMapping("/remove")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String removeForm(Long itemId, Model model) throws Exception {
        Item item = itemService.getItem(itemId);
        model.addAttribute("item", item);
        return "/item/remove";
    }
    @PostMapping("/remove")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String remove(Item item, RedirectAttributes rttr) throws Exception {
        itemService.remove(item.getItemId());

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/item/list";
    }
    @PostMapping("/modify")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String modify(Item item, RedirectAttributes rttr) throws Exception {
        MultipartFile pictureFile = item.getPicture();

        if (pictureFile != null && pictureFile.getSize() > 0) {
            String createdFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());

            item.setPictureUrl(createdFilename);
        }

        MultipartFile previewFile = item.getPreview();

        if (previewFile != null && previewFile.getSize() > 0) {
            String createdFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

            item.setPreviewUrl(createdFilename);
        }

        itemService.modify(item);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/item/list";
    }
    private MediaType getMediaType(String formatName){
        if(formatName != null) {
            if(formatName.equals("JPG")) {
                return MediaType.IMAGE_JPEG;
            }

            if(formatName.equals("GIF")) {
                return MediaType.IMAGE_GIF;
            }

            if(formatName.equals("PNG")) {
                return MediaType.IMAGE_PNG;
            }
        }

        return null;
    }
}
