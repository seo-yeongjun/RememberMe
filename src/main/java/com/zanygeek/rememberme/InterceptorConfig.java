package com.zanygeek.rememberme;

import com.zanygeek.rememberme.interceptor.MemberInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    MemberInterceptor memberInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberInterceptor).order(1).addPathPatterns("/**").excludePathPatterns("/css/**",
                "/js/**", "/", "/login", "/logout", "/*.ico", "/error", "/error/**",
               "/img/**");
    }
}
