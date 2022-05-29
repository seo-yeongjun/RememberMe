package com.zanygeek.rememberme.controller.member;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.zanygeek.rememberme.SessionConst;
import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.NaverForm;
import com.zanygeek.rememberme.entity.NaverLoginBO;
import com.zanygeek.rememberme.form.FindForm;
import com.zanygeek.rememberme.form.LoginForm;
import com.zanygeek.rememberme.service.JoinService;
import com.zanygeek.rememberme.service.LoginService;
import com.zanygeek.rememberme.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
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
    @Autowired
    MemberService memberService;

    @GetMapping("login")
    public String login(Model model, @ModelAttribute("form") LoginForm form, @RequestParam(required = false, defaultValue = "") String redirectURL, HttpSession session) {
        if (!redirectURL.equals(""))
            model.addAttribute("redirectURL", redirectURL);
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session, redirectURL);
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

    @GetMapping("find")
    public String find(Model model, @ModelAttribute("idForm") FindForm findForm, @ModelAttribute("passwordForm") FindForm findPasswordForm, @ModelAttribute String starId) {
        return "member/find";
    }

    @PostMapping("find/id")
    public String findId(@ModelAttribute("idForm") FindForm findForm, RedirectAttributes redirectAttributes) throws MessagingException {
        if (memberService.memberIdExist(findForm)) {
            memberService.sendTemporaryPassword(findForm);
            redirectAttributes.addFlashAttribute("starId", "아이디 : " + memberService.findStarUserIdByMemberIdForm(findForm));
        } else {
            redirectAttributes.addFlashAttribute("starId", "해당하는 아이디가 없습니다.");
        }
        return "redirect:/find" + "#findId";
    }

    @PostMapping("find/password")
    public String findPassword(@ModelAttribute("passwordForm") FindForm findPasswordForm, RedirectAttributes redirectAttributes) throws MessagingException {
        if (memberService.memberPasswordExist(findPasswordForm)) {
            memberService.sendTemporaryPassword(findPasswordForm);
            redirectAttributes.addFlashAttribute("passwordConfirm", "이메일로 임시 비밀번호를 발신하였습니다.\n로그인 이후에 꼭 비밀번호를 변경해 주세요.");
        } else {
            redirectAttributes.addFlashAttribute("passwordConfirm", "해당하는 정보를 찾을 수 없습니다. 다시 입력해 주세요.");
        }
        return "redirect:/find" + "#findPassword";
    }

    @GetMapping("login/callback")
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
    public String logout(HttpServletRequest request, @RequestParam(required = false, defaultValue = "") String redirectURL) {
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
