package com.kmoonwang.mywenda.interceptor;

import com.kmoonwang.mywenda.dao.LoginTicketDAO;
import com.kmoonwang.mywenda.dao.UserDAO;
import com.kmoonwang.mywenda.model.HostHolder;
import com.kmoonwang.mywenda.model.LoginTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 未登录注册拦截器
 * 当点击某个页面的时候，会跳到登录注册页面，
 * 当登录注册完成以后会自动跳回到原来要访问的那个页面
 */

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserDAO userDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //现在这个页面是需要登录的，如果没有登录，就跳转到登录注册页面
        if(hostHolder.getUser() == null){
            //将当前访问的页面作为一个参数传过去
            response.sendRedirect("/reglogin?next=" + request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }



}
