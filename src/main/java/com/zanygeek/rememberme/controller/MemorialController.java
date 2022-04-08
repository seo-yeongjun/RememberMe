package com.zanygeek.rememberme.controller;

import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Photo;
import com.zanygeek.rememberme.repository.MemberRepository;
import com.zanygeek.rememberme.repository.MemorialRepository;
import com.zanygeek.rememberme.repository.PhotoRepository;
import com.zanygeek.rememberme.service.MemorialService;
import com.zanygeek.rememberme.service.PhotoService;
import com.zanygeek.rememberme.service.UploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("memorial")
@Log4j2
public class MemorialController {
    @Autowired
    MemorialService memorialService;
    @Autowired
    PhotoService photoService;

    @GetMapping("new")
    public String newMemorial(Model model, @SessionAttribute(name = "member", required = true) Member member, Memorial memorial,
                              @ModelAttribute MultipartFile photo) {
        model.addAttribute("member", member);
        model.addAttribute("memorial", memorial);
        return "memorial/edit/addMemorial";
    }

    @PostMapping("new")
    public String newMemorial(@SessionAttribute(name = "member", required = true) Member member, @ModelAttribute Memorial memorial, @ModelAttribute MultipartFile uploadPhoto) {
        Memorial savedMemorial = memorialService.save(memorial, member);
        try {
            photoService.saveMainPhoto(uploadPhoto, savedMemorial);
        } catch (Exception e) {
            log.error("에러 발생:" + e);
        }


        return "redirect:/";
    }
}
