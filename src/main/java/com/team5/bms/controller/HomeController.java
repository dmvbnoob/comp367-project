package com.team5.bms.controller;
 
import com.team5.bms.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String showHomePage() {
        return "index";
    }

}