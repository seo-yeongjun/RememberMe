package com.zanygeek.rememberme.controller.member;

import com.zanygeek.rememberme.SessionConst;
import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.MemberToken;
import com.zanygeek.rememberme.form.JoinForm;
import com.zanygeek.rememberme.repository.MemberRepository;
import com.zanygeek.rememberme.repository.MemberTokenRepository;
import com.zanygeek.rememberme.service.JoinService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/join")
@Log4j2
public class JoinController {
    @Autowired
    JoinService joinService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberTokenRepository memberTokenRepository;

    //join get
    @GetMapping
    public String join(@ModelAttribute("form") JoinForm form) {
        return "member/join";
    }

    //join post
    @PostMapping
    public String join(Model model, @ModelAttribute("form") @Validated JoinForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        //validation 검사
        if (joinService.error(form, bindingResult)) {
            model.addAttribute(form);
            return "member/join";
        }
        //검사 성공 시
        else {
            joinService.joinMember(form);
            redirectAttributes.addFlashAttribute("mail", form.getEmail());
            return "redirect:/join/joinSuccess"; //to do 조인 성공 페이지 redirectAttribute
        }
    }
    @GetMapping("joinSuccess")
    public String join(Model model) {
        return "member/joinSuccess";
    }

    //메일 토큰 확인
    @RequestMapping("/confirmMail")
    public String confirmMail(@RequestParam("token") String token, HttpServletRequest request) {
        MemberToken checkToken = memberTokenRepository.findByConfirmToken(token);
        if (token != null) {
            Member member = memberRepository.findByUserId(checkToken.getUserId());
            member.setEnabled(true);
            try {
                memberRepository.save(member);
            } catch (Exception e) {
                log.error("에러 발생:" + e);
            }
            request.getSession().setAttribute(SessionConst.member, member);
            return "redirect:/";
        } else {
            return "error";
        }
    }
}
