package com.kmoonwang.mywenda.controller;


import com.kmoonwang.mywenda.aspect.LogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;


//@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    @RequestMapping(path={"/","index"})
    @ResponseBody//返回字符串而不是模板
    public String inde(HttpSession httpSession){
        String k = "Hello xueqing! " + httpSession.getAttribute("msg");
        logger.info("Visit Home");
        return k;
    }

    //解析路径里的参数
    @RequestMapping(path={"/profile/{groupid}"},method = {RequestMethod.POST})
    @ResponseBody//返回字符串而不是模板
    public String profil(@PathVariable("groupid") String groupid){
        return String.format("profile page of %s ",groupid);
    }

    //解析请求里的参数
    @RequestMapping(path={"/profile/{grouid}/{useid}"})
    @ResponseBody//返回字符串而不是模板
    public String profile1(@PathVariable("useid") int useid,
                           @PathVariable("grouid") String grouid,
                           @RequestParam(value="type",required = false) String type,
                           @RequestParam(value = "key",defaultValue = "1",required = false) String key){
        return String.format("profile page of %s / %d, t:%d,k:%s",grouid,useid,type,key);
    }

    @RequestMapping(path={"/kk"})
    public String template(Model model){
        model.addAttribute("name","xueqing");
        return "home";
    }

    @RequestMapping(path={"/tt"})
    @ResponseBody
    public String templat(Model model, HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionid){
        StringBuilder sb = new StringBuilder();
        sb.append("COOKIEVALUE:" + sessionid);
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            //sb.append(name + ":" + "<br>");
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }

        if(request.getCookies() != null){
            for(Cookie cookie:request.getCookies()){
                sb.append("Cookie:" + cookie.getName() + "value" + cookie.getValue());
            }
        }
        sb.append(request.getMethod() + "<br>");//<br>是指回车的意思
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURI() + "<br>");

        //reaopnse是我们返回给用户的
        response.addHeader("xueqing","I love you");
        response.addCookie((new Cookie("username","nowcoder")));
            httpSession.setAttribute("msg","jump from redirect");
            //response.sendRedirect("/");
        //return "redirect:/";//如果有@Requestbody就无法实现跳转
        return sb.toString();
    }

    @RequestMapping(path={"/redirec"},method = {RequestMethod.GET})
    public String redire(HttpSession httpSession){
        httpSession.setAttribute("msg","jump form redirect");
        return "redirect:/";//一定不能加@RequestBody
    }

    @RequestMapping(path={"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam(value="key") String key){
        if("admin".equals(key))
            return "Hello xueqing!";
        throw new IllegalArgumentException("参数不对");
    }

    //异常捕获
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error" + e.getMessage();
    }

}
