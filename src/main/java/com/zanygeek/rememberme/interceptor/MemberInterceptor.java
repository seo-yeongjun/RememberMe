package com.zanygeek.rememberme.interceptor;

import com.zanygeek.rememberme.SessionConst;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class MemberInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session==null||session.getAttribute(SessionConst.member) == null) {
            response.sendRedirect("/login");
            return false;
        } else
            return true;
    }
}
