package com.zanygeek.rememberme.controller;

import com.zanygeek.rememberme.SessionConst;
import com.zanygeek.rememberme.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @SessionAttribute(name = SessionConst.member, required = false) Member member){
        if(member!=null){
            model.addAttribute("member", member);
            System.out.println(member);
        }
        return "index";
    }
}
