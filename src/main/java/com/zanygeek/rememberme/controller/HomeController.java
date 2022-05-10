package com.zanygeek.rememberme.controller;

import com.zanygeek.rememberme.SessionConst;
import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Question;
import com.zanygeek.rememberme.form.EditMemorialsForm;
import com.zanygeek.rememberme.repository.QuestionRepository;
import com.zanygeek.rememberme.service.MemorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    MemorialService memorialService;

    @GetMapping(value = {"/"})
    public String home(Model model, @SessionAttribute(name = SessionConst.member, required = false) Member member, @ModelAttribute Question question) {
        if (member != null) {
            model.addAttribute("member", member);
        }
        return "index";
    }

    @PostMapping("/contact")
    public String contact(Model model, @SessionAttribute(name = SessionConst.member, required = false) Member member, @Validated Question question, BindingResult bindingResult, RedirectAttributes attr) {
        if (member != null) {
            model.addAttribute("member", member);
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            model.addAttribute("anchorQuestion", true);
            return "index";
        }
        questionRepository.save(question);
        attr.addFlashAttribute("contactSuccess", true);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchName, RedirectAttributes attr) {
        if(!searchName.isEmpty())
        attr.addFlashAttribute("memorials", memorialService.getMemorialsByName(searchName));
        attr.addFlashAttribute("anchorSearch", true);
        return "redirect:/";
    }
}
