package com.example.reflect.controller;

import com.example.reflect.annotation.RequestMapping;
import com.example.reflect.controller.dto.JoinDto;
import com.example.reflect.controller.dto.LoginDto;

public class UserController {

    @RequestMapping("/")
    public String index() {
        System.out.println("index() function call");
        return "/";
    }

    @RequestMapping("/join")
    public String join(JoinDto dto) {
        System.out.println("join() function call");
        return "/";
    }

    @RequestMapping("/login")
    public String login(LoginDto dto) {
        System.out.println("login() function call");
        return "/";
    }

    @RequestMapping("/user")
    public String user() {
        System.out.println("user() function call");
        return "/";
    }
}
