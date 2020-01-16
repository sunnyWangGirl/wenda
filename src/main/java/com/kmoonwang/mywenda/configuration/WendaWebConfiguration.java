package com.kmoonwang.mywenda.configuration;


import com.kmoonwang.mywenda.interceptor.LoginRequiredInterceptor;
import com.kmoonwang.mywenda.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter {
    //将interceptor配置到webconfigure里
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(passportInterceptor);
        //因为要使用passportInterceptor里面定义的变量，所以下面的拦截器要放在后面
        //还要加上参数，当我访问用户页面的时候会进行相应的拦截
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
