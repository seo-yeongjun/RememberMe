package com.zanygeek.rememberme.controller;

import com.zanygeek.rememberme.SessionConst;
import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Photo;
import com.zanygeek.rememberme.service.MemorialService;
import com.zanygeek.rememberme.service.PhotoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("memorial")
@Log4j2
public class ManageMemorialController {
    @Autowired
    MemorialService memorialService;
    @Autowired
    PhotoService photoService;

    //추모 공관 리스트
    @GetMapping("edit")
    public String home(Model model, @SessionAttribute(name = SessionConst.member, required = true) Member member) {
        model.addAttribute("member", member);
        model.addAttribute("memorials", memorialService.getMemorialsFormByMemberId(member.getId()));
        return "memorial/edit/editMemorials";
    }

    //추모 공간 관리 페이지 get
    @GetMapping("edit/{memorialId}")
    public String home(Model model, @PathVariable int memorialId, @SessionAttribute(name = SessionConst.member, required = true) Member member, @ModelAttribute Photo photo) {
        Memorial memorial = memorialService.getMemorialById(memorialId);
        if (memorial.getMemberId() == member.getId()) {
            String mainText = memorial.getMainText();
            if (mainText != null)
                memorial.setMainText(mainText.replaceAll("<br>", "\n"));
            model.addAttribute("memorial", memorial);
            model.addAttribute("member", member);
            model.addAttribute("mainImg", photoService.getMainPhoto(memorial));
            return "memorial/edit/editMemorial";
        } else
            return "redirect:/";
    }

    //메인 사진 변경 post
    @PostMapping("edit/{memorialId}/mainPhoto")
    public String editPhoto(MultipartFile photo, @PathVariable int memorialId, @SessionAttribute(name = SessionConst.member, required = true) Member member) {
        Memorial memorial = memorialService.getMemorialById(memorialId);
        if (memorial.getMemberId() == member.getId()) {
            Photo existingPhoto = photoService.getMainPhoto(memorial);
            photoService.deletePhoto(existingPhoto);
            try {
                photoService.saveMainPhoto(photo, memorial);
            } catch (Exception e) {
                log.error("에러 발생:" + e);
            }
        }
        return "redirect:/memorial/edit/" + memorialId;
    }

    //메인 문구 변경 post
    @PostMapping("edit/{memorialId}/mainText")
    public String editMainText(@PathVariable int memorialId, @SessionAttribute(name = SessionConst.member, required = true) Member member, String mainText) {
        Memorial memorial = memorialService.getMemorialById(memorialId);
        if (memorial.getMemberId() == member.getId()) {
            memorial.setMainText(mainText.replaceAll("\r\n", "<br>"));
            memorialService.update(memorial);
        }
        return "redirect:/memorial/edit/" + memorialId;
    }

    //사진 삭제 post
    @PostMapping("edit/{memorialId}/deletePhoto")
    public String deletePhoto(@PathVariable int memorialId, @SessionAttribute(name = SessionConst.member, required = true) Member member, Photo photo) {
        Memorial memorial = memorialService.getMemorialById(memorialId);
        if (memorial.getMemberId() == member.getId()) {
            photoService.deletePhotoByUrl(photo.getUrl());
        }
        return "redirect:/memorial/edit/" + memorialId;
    }
}
