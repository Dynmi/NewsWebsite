package com.example.demo.interceptor;
import com.example.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    private  final Logger log = LoggerFactory.getLogger(AdminInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("==========管理员拦截");
        HttpSession session = request.getSession();
        log.info("sessionId为：" + session.getId());        // 获取用户信息，如果没有用户信息直接返回提示信息
        Object userInfo = session.getAttribute("user");
        if (userInfo == null) {
            log.info("没有登录");
            response.sendRedirect("/login.html");
            return false;
        } else {
            //判断用户权限
            User user = (User) session.getAttribute("user");
            int permit = user.getPermission();
            if (permit == 0) {
                log.info("未拥有管理员权限！");
                response.sendRedirect("/index.html");
                return false;
            } else {
                log.info("拥有管理员权限！：");
            }
            return true;

        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }

}

