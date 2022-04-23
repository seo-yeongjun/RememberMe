package com.zanygeek.rememberme.interceptor;

import com.zanygeek.rememberme.SessionConst;
import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Memorial;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class MemorialInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        HttpSession session = request.getSession(false);
        Member member = null;
        Memorial memorial = null;
        if (session != null) {
            member = (Member) session.getAttribute(SessionConst.member);
        }
        if (modelAndView != null) {
            memorial = (Memorial) modelAndView.getModel().get("memorial");
        }
        if (member != null && memorial != null && memorial.getMemberId() == member.getId()) {
            request.setAttribute("owner", true);
        }
    }
}
