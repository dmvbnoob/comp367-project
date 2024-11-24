package com.team5.bms.controller;
 
import jakarta.validation.Valid;
import com.team5.bms.model.Card;
import com.team5.bms.model.User;
import org.springframework.ui.Model;
import com.team5.bms.model.Building;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;

    @GetMapping("/register")
    public String showRegisterPage(HttpServletRequest request, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("building", new Building());
        model.addAttribute("card", new Card());
        baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
        @Valid @ModelAttribute("user") User user, 
        @Valid @ModelAttribute("building") Building building, 
        @Valid @ModelAttribute("card") Card card, 
        BindingResult result, 
        Model model) {
        
        if (result.hasErrors()) {
            return "register"; // returns the form view if there are validation errors
        }

        // Save Building Details
        System.out.println("UserController - POST - register - building -> " + building);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Building> entity = new HttpEntity<>(building, headers);
            ResponseEntity<Building> response = restTemplate.exchange(baseUrl+"/api/buildings", HttpMethod.POST, entity, Building.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Building createdBuilding = response.getBody();
                System.out.println("Building created successfully: " + createdBuilding);
                user.setBuilding(createdBuilding); 
                //model.addAttribute("buildingId", createdBuilding.getId());
            } else {
                // Handle error if creation fails
                model.addAttribute("message", "Failed to create building.");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error creating building: " + e.getMessage());
            return "register";
        }

        // Save Card Details
        System.out.println("UserController - POST - register - card -> " + card);

        // Save Building Owner User details with its Building and Card
        System.out.println("UserController - POST - register - Building Owner - user -> " + user);

        // Set the user’s building and card
        // user.setBuilding(building);
        // card.setUser(user);

        // Save the user and card (and building if necessary)
        // userService.save(user); // Assuming you have a service to save the entities
        // userService.saveCard(card);

        model.addAttribute("message", "Registration successful!");
        return "redirect:/login"; // redirect to login page after successful registration
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

}