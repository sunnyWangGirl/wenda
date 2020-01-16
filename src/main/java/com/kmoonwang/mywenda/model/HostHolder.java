package com.kmoonwang.mywenda.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    //多线程同时访问用户信息
    //使用ThreadLocal，每个线程都会有一个对象的拷贝，内存地址也不同的
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser(){
        return users.get();//返回与当前线程关联的那个对象
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }

}
