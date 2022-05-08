package com.zanygeek.rememberme.controller.member;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.zanygeek.rememberme.SessionConst;
import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.NaverForm;
import com.zanygeek.rememberme.entity.NaverLoginBO;
import com.zanygeek.rememberme.form.LoginForm;
import com.zanygeek.rememberme.service.JoinService;
import com.zanygeek.rememberme.service.LoginService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Log4j2
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    JoinService joinService;
    @Autowired
    NaverLoginBO naverLoginBO;

    @GetMapping("login")
    public String login(Model model, @ModelAttribute("form") LoginForm form, @RequestParam(required = false, defaultValue = "") String redirectURL, HttpSession session) {
        if (!redirectURL.equals(""))
            model.addAttribute("redirectURL", redirectURL);
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session,redirectURL);
        model.addAttribute("naverUrl", naverAuthUrl);
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
        if (redirectURL.isEmpty())
            return "redirect:/";
        else
            return "redirect:" + redirectURL;

    }

    @GetMapping( "login/callback")
    public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session, @RequestParam(required = false, defaultValue = "") String redirectURL)
            throws IOException, ParseException {
        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        NaverForm form = naverLoginBO.getNaverForm(oauthToken);
        Member member = joinService.joinNaverMember(form);
        if (member == null) {
            return "error";
        } else {
            session.setAttribute(SessionConst.member, member);
        }
        if (redirectURL.isEmpty())
            return "redirect:/";
        else
            return "redirect:" + redirectURL;
    }


    @GetMapping("logout")
    public String logout(HttpServletRequest request,@RequestParam(required = false, defaultValue = "") String redirectURL) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(SessionConst.member);
        }
        if (redirectURL.isEmpty())
            return "redirect:/";
        else
            return "redirect:" + redirectURL;
    }
}
