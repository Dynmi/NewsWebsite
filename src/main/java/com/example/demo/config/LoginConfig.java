package com.example.demo.config;

import com.example.demo.interceptor.AdminInterceptor;
import com.example.demo.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/extra_profile.html","/page_todo.html","/contributesubmit.html");
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admincenter.html","/admincenter","/newsmanage.html", "/newsmanage","/newsmanage/list","/admincenter/list","/admincenter/newsmanage.html","/newsedit","news_publish");
    }
}
