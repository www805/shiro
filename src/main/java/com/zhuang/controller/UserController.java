package com.zhuang.controller;

import com.zhuang.domain.User;
import com.zhuang.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Security;
import java.util.Collection;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionDAO sessionDAO;

    @GetMapping("/index")
    public ModelAndView index(){

        ModelAndView view = new ModelAndView();

//        User user = userService.findById(1);
//        System.out.println(user);

        Collection<Session> sessions = sessionDAO.getActiveSessions();

        view.setViewName("index");
        view.addObject("key","你好吗");
        view.addObject("sessionSize", sessions.size());

        return view;
    }

    @GetMapping("/add")
    public String add(){
        return "add";
    }

    @GetMapping("/update")
    public String update(){
        return "update";
    }

    @GetMapping("/noAuth")
    public String noAuth(){
        return "noAuth";
    }

    @GetMapping("/logout")
    public String logout(Model model){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg", "安全退出");
        return "logout";
    }

    @RequestMapping("/login")
    public String login(String name, String password, Model model){

        Subject subject = SecurityUtils.getSubject();

        //创建token对象，把接收到的账号密码传入
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);

        try {
            //开始登录验证
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
