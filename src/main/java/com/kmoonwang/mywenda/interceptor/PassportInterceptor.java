package com.kmoonwang.mywenda.interceptor;

import com.kmoonwang.mywenda.dao.LoginTicketDAO;
import com.kmoonwang.mywenda.dao.UserDAO;
import com.kmoonwang.mywenda.model.HostHolder;
import com.kmoonwang.mywenda.model.LoginTicket;
import com.kmoonwang.mywenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component//要加上这个注解才能进行依赖注入呀
public class PassportInterceptor implements HandlerInterceptor {
    //用户身份验证拦截器
    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    HostHolder hostholder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //先验证ticket
        String ticket = null;
        if(request.getCookies()!=null){
            for(Cookie cookie:request.getCookies()){
                if(cookie.getName().equals("ticket")){
                    //找到ticket,但还不能说明这个ticket是有效的，有可能它已经过期了
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if(ticket != null){
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if(loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0){
                return true;//返回false的话，表明整个请求就结束了，不会再执行下去了
            }
            //如果ticket是有效的，就把相关的用户信息取出来，放到上下文中
            User user = userDAO.selectById(loginTicket.getUserId());
            hostholder.setUser(user);
        }
        return true;//返回false的话，表示失败了，被拦截掉了
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //handler处理完了以后,在渲染之前
        if(modelAndView != null){
            modelAndView.addObject("user",hostholder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //渲染完了清除数据
        hostholder.clear();
    }
}
