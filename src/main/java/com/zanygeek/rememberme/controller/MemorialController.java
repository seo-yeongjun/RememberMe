package com.zanygeek.rememberme.controller;

import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Obituary;
import com.zanygeek.rememberme.entity.Wreath;
import com.zanygeek.rememberme.form.UploadPhotosForm;
import com.zanygeek.rememberme.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("memorial")
@Log4j2
public class MemorialController {
    @Autowired
    MemorialService memorialService;
    @Autowired
    PhotoService photoService;
    @Autowired
    WreathService wreathService;
    @Autowired
    ObituaryService obituaryService;
    @Autowired
    MapService mapService;

    //추모관 생성 get
    @GetMapping("new")
    public String newMemorial(Model model, @SessionAttribute(name = "member", required = false) Member member, Memorial memorial,
                              @ModelAttribute MultipartFile photo, RedirectAttributes redirectAttributes) {
        if(member==null){
            redirectAttributes.addFlashAttribute("loginMessage", true);
            redirectAttributes.addFlashAttribute("redirectURL", "/memorial/new");
            return "redirect:/login";
        }
        model.addAttribute("member", member);
        model.addAttribute("memorial", memorial);
        return "memorial/edit/addMemorial";
    }

    //추모관 생성 post
    @PostMapping("new")
    public String newMemorial(Model model, @SessionAttribute(name = "member") Member member, @Validated @ModelAttribute Memorial memorial, @ModelAttribute MultipartFile uploadPhoto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            model.addAttribute(memorial);
            return "memorial/edit/addMemorial";
        }
        Memorial savedMemorial = memorialService.save(memorial, member);
        try {
            photoService.saveMainPhoto(uploadPhoto, savedMemorial);
        } catch (Exception e) {
            log.error("에러 발생:" + e);
        }
        return "redirect:/memorial/"+savedMemorial.getId();
    }

    //추모공간 get
    @GetMapping("{memorialId}")
    public String momirlal(Model model, @PathVariable int memorialId, @SessionAttribute(name = "member", required = false) Member member,
                           @ModelAttribute Wreath wreath, @ModelAttribute Obituary obituary, @ModelAttribute UploadPhotosForm uploadPhotosForm) {
        Memorial memorial = memorialService.getMemorialById(memorialId);
        if (memorial == null)
            return "error";
        model.addAttribute("member", member);
        model.addAttribute("memorial", memorial);
        model.addAttribute("mainImg", photoService.getMainPhoto(memorial));
        model.addAttribute("map", mapService.getMap(memorialId));
        model.addAttribute("obituaries", obituaryService.getObituaryForms(memorialId));
        model.addAttribute("photos", photoService.getPhotosByMemorialId(memorialId));
        return "memorial/memorial";
    }

    //헌화하기 iframe을 위한 get
    @GetMapping("{memorialId}/wreath")
    public String wreath(Model model, @PathVariable int memorialId) {
        model.addAttribute("wreaths", wreathService.getWreaths(memorialId));
        return "memorial/flowers";
    }

    //헌화하기 post
    @PostMapping("{memorialId}/wreath")
    public String wreath(Wreath wreath, @PathVariable int memorialId) {
        wreathService.saveWreath(wreath, memorialId);
        return "redirect:/memorial/" + memorialId + "#wreath";
    }

    //기억공유 post
    @PostMapping("{memorialId}/obituary")
    public String wreath(@PathVariable int memorialId, Obituary obituary, List<MultipartFile> photos) {
        Obituary savedObituary = obituaryService.saveObituary(obituary, memorialId);
        try {
            if (!photos.get(0).isEmpty())
                photoService.savePhotos(photos, memorialId, savedObituary, obituary.getName());
        } catch (Exception e) {
            log.error("에러 발생: " + e);
        }
        return "redirect:/memorial/" + memorialId + "#obituary";
    }

    //사진 업로드 post
    @PostMapping("{memorialId}/photos")
    public String uploadPhotos(@PathVariable int memorialId, List<MultipartFile> photos, UploadPhotosForm uploadPhotosForm){
        try {
            if (!photos.get(0).isEmpty())
                photoService.savePhotos(photos, memorialId, uploadPhotosForm);
        } catch (Exception e) {
            log.error("에러 발생: " + e);
        }
        return "redirect:/memorial/"+memorialId+"#photo";
    }
}
