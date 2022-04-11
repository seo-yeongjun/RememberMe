package com.zanygeek.rememberme.controller;

import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Wreath;
import com.zanygeek.rememberme.repository.MemorialRepository;
import com.zanygeek.rememberme.repository.WreathRepository;
import com.zanygeek.rememberme.service.MemorialService;
import com.zanygeek.rememberme.service.PhotoService;
import com.zanygeek.rememberme.service.WreathService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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
    MemorialRepository memorialRepository;
    @Autowired
    WreathService wreathService;
    @Autowired
    WreathRepository wreathRepository;

    //추모관 생성 get
    @GetMapping("new")
    public String newMemorial(Model model, @SessionAttribute(name = "member", required = true) Member member, Memorial memorial,
                              @ModelAttribute MultipartFile photo) {
        model.addAttribute("member", member);
        model.addAttribute("memorial", memorial);
        return "memorial/edit/addMemorial";
    }

    //추모관 생성 post
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
   //추모공간 get
    @GetMapping("{memorialId}")
    public String momirlal(Model model, @PathVariable int memorialId, @SessionAttribute(name = "member", required = false) Member member, @ModelAttribute Wreath wreath) {
        Memorial memorial = memorialRepository.findById(memorialId).orElse(null);
        if (memorial == null)
            return "error";
        model.addAttribute("memorial", memorial);
        model.addAttribute("mainImg",photoService.getMainPhoto(memorial));
        return "memorial/memorial";
    }

    //헌화하기 get
    @GetMapping("{memorialId}/wreath")
    public String wreath(Model model,@PathVariable int memorialId){
        List<Wreath> wreaths = wreathRepository.findAllByMemorialIdOrderByIdDesc(memorialId);
        model.addAttribute("wreaths", wreaths);
        return "memorial/flowers";
    }

    //헌화하기 post
    @PostMapping("{memorialId}/wreath")
    public String wreath(Model model, Wreath wreath, @PathVariable int memorialId){
        wreathService.saveWreath(wreath, memorialId);
        return "redirect:/memorial/"+memorialId+"#wreath";
    }
}
