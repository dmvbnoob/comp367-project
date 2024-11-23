package com.team5.bms.controller;
 
import com.team5.bms.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

}