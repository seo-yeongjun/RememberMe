package com.zanygeek.rememberme.controller.member;

import com.zanygeek.rememberme.SessionConst;
import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.form.LoginForm;
import com.zanygeek.rememberme.service.LoginService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Log4j2
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("login")
    public String login(Model model, @ModelAttribute("form") LoginForm form,@RequestParam(required = false, defaultValue = "") String redirectURL) {
            if(!redirectURL.equals(""))
            model.addAttribute("redirectURL", redirectURL);
        return "member/login";
    }

    @PostMapping("login")
    public String login(Model model, @Validated @ModelAttribute("form") LoginForm form, BindingResult bindingResult, HttpSession session, @ModelAttribute("redirectURL") String redirectURL) {
        if (loginService.error(form, bindingResult)) {
            model.addAttribute(form);
            return "member/login";
        } else {
            Member member = loginService.login(form.getUserId(), form.getPassword());
            if (member == null) {
                return "error";
            } else {
                session.setAttribute(SessionConst.member, member);
            }
        }
        if(redirectURL.isEmpty())
            return "redirect:/";
      else
          return "redirect:"+redirectURL;

    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(SessionConst.member);
        }
        return "redirect:/";
    }
}
