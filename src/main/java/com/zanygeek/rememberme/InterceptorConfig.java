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
        registry.addInterceptor(memberInterceptor).order(1).addPathPatterns("/**","/memorial/new","/memorial/edit").excludePathPatterns("/css/**",
                "/js/**", "/", "/login", "/logout", "/*.ico", "/error", "/error/**","/memorial/**","/join","/join/**",
               "/img/**");
        registry.addInterceptor(memorialInterceptor).order(2).addPathPatterns("/memorial/**");
    }
}
