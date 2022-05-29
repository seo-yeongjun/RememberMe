package com.zanygeek.rememberme;

import com.zanygeek.rememberme.interceptor.MemberInterceptor;
import com.zanygeek.rememberme.interceptor.MemorialInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    MemberInterceptor memberInterceptor;
    @Autowired
    MemorialInterceptor memorialInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberInterceptor).order(1).addPathPatterns("/**").excludePathPatterns("/css/**",
                "/js/**", "/", "/login","/login/**","/contact", "/logout", "/*.ico", "/error", "/error/**","/memorial/**","/join","/join/**",
               "/img/**","/search","/edit/email/unsubscribe/**","/개인정보처리방침.html","/이용약관.html","/find/**","/find");
        registry.addInterceptor(memorialInterceptor).order(2).addPathPatterns("/memorial/**").excludePathPatterns("/memorial/example","/memorial/example/**");
    }
}
