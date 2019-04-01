package com.zhuang.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Security;

@Controller
public class UserController {

    @GetMapping("/index")
    public ModelAndView index(){

        ModelAndView view = new ModelAndView();

        view.setViewName("index");
        view.addObject("key","你好吗");

        return view;
    }

    @GetMapping("/add")
    public String add(){
        return "add";
    }


    @RequestMapping("/login")
    public String login(String name, String password, Model model){

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(name, password);

        try {
            subject.login(token);

            return "redirect:/index";
        } catch (UnknownAccountException e) {

            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e){
            model.addAttribute("msg", "用户名或密码错误");
            return "login";
        }


    }

}
