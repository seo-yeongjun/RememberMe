package com.zanygeek.rememberme.controller;

import com.zanygeek.rememberme.SessionConst;
import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Question;
import com.zanygeek.rememberme.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class HomeController {
@Autowired
    QuestionRepository questionRepository;
    @GetMapping(value={"/contact","/"})
    public String home(Model model, @SessionAttribute(name = SessionConst.member, required = false) Member member, @ModelAttribute Question question){
        if(member!=null){
            model.addAttribute("member", member);
        }
        return "index";
    }

    @PostMapping("/contact")
    public String home(Model model, @SessionAttribute(name = SessionConst.member, required = false) Member member, @Validated Question question, BindingResult bindingResult, RedirectAttributes attr){
        if(member!=null){
            model.addAttribute("member", member);
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("question",question);
            model.addAttribute("anchor", true);
            return "index";
        }
        questionRepository.save(question);
        attr.addFlashAttribute("contactSuccess", true);
        return "redirect:/";
    }
}
