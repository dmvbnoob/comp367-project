package com.team5.bms.controller;
 
import com.team5.bms.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/** @author Stephanie Santos
 * Handles the root URL ("/") for non-logged-in users.
 * This is the public home page, distinct from the logged-in user's dashboard.
 * The logged-in user's dashboard is handled by UserController at "/index".
 */

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/privacy")
    public String showPrivacyPolicyPage() {
        return "privacy";
    }

}