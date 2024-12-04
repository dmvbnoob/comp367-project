package com.team5.bms.controller;
 
import jakarta.validation.Valid;
import com.team5.bms.model.Card;
import com.team5.bms.model.User;
import com.team5.bms.model.enumeration.Roles;

import org.springframework.ui.Model;
import com.team5.bms.model.Building;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper; // This is NOT needed

    private String baseUrl;
    
    @GetMapping("/delete-user/{id}")
    public String showDeleteUserPage(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
    	
    	baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("UserController - GET - showDeleteUserPage - user.id" + id);

        try {
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users/" + id, HttpMethod.GET, null, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                User user = response.getBody();
                System.out.println("UserController - GET - showDeleteUserPage -> user -> " + user);
                model.addAttribute("user", user);
                // List<Roles> roles = Arrays.asList(Roles.values());
                List<Roles> roles = Arrays.stream(Roles.values())
                        .filter(role -> role != Roles.OWNER)
                        .collect(Collectors.toList());
                model.addAttribute("roles", roles);
                return "user-delete";
            } else {
                model.addAttribute("message", "Get User By Id Failed");
                return "redirect:/users";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error on Get User By Id: " + e.getMessage());
            return "redirect:/users";
        }
              
    }
    
    @PostMapping("/delete-user")
    public String deleteUser(@Valid @ModelAttribute("user") User user, HttpSession session, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            return "user-delete"; // Returns the form view if there are validation errors
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Edit BMS User details
        System.out.println("UserController - POST - deleteUser - user -> " + user);
        try {
            HttpEntity<User> entity = new HttpEntity<>(user, headers);
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users/" + user.getId(), HttpMethod.DELETE, entity, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                //User bmsUser = response.getBody();
                System.out.println("UserController - POST - deleteUser - response.getStatusCode() -> " + response.getStatusCode());
                // user = bmsUser;
            } else {
                model.addAttribute("message", "Failed to Delete User");
                return "redirect:/users";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error on Delete User: " + e.getMessage());
            return "redirect:/users";
        }

        model.addAttribute("user", user);
        model.addAttribute("message", "Delete User Successful!");
        // return "user-deleted";
        return "redirect:/users";
        
    }
    
    
    @GetMapping("/edit-user/{id}")
    public String showEditUserPage(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
    	
    	baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("UserController - GET - request - user.id" + id);

        try {
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users/" + id, HttpMethod.GET, null, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                User user = response.getBody();
                System.out.println("UserController - GET - response -> user -> " + user);
                model.addAttribute("user", user);
                // List<Roles> roles = Arrays.asList(Roles.values());
                List<Roles> roles = Arrays.stream(Roles.values())
                        .filter(role -> role != Roles.OWNER)
                        .collect(Collectors.toList());
                model.addAttribute("roles", roles);
                return "user-edit";
            } else {
                model.addAttribute("message", "Get user by id failed.");
                return "users";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error getting user by id: " + e.getMessage());
            return "users";
        }
              
    }
    
    @PostMapping("/edit-user")
    public String editUser(@Valid @ModelAttribute("user") User user, HttpSession session, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            return "create-user"; // Returns the form view if there are validation errors
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        Building building = (Building) session.getAttribute("buildingOfLoggedInUser");
        user.setBuilding(building);

        // Edit BMS User details
        System.out.println("UserController - POST - editUser - request - user -> " + user);
        try {
            HttpEntity<User> entity = new HttpEntity<>(user, headers);
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users/" + user.getId(), HttpMethod.PUT, entity, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                User bmsUser = response.getBody();
                System.out.println("UserController - POST - editUser - response - bmsUser -> " + bmsUser);
                user = bmsUser;
            } else {
                model.addAttribute("message", "Failed to edit BMS User.");
                return "user-edit";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error editing BMS User: " + e.getMessage());
            return "user-edit";
        }

        model.addAttribute("user", user);
        model.addAttribute("message", "BMS User edited successful!");
        return "user-edited";
    }
    
    @GetMapping("/create-user")
    public String showCreateUserPage(HttpServletRequest request, Model model) {
    	
        model.addAttribute("user", new User());
        baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        // List<Roles> roles = Arrays.asList(Roles.values());
        List<Roles> roles = Arrays.stream(Roles.values())
                .filter(role -> role != Roles.OWNER)
                .collect(Collectors.toList());
        model.addAttribute("roles", roles);
        return "user-create";
        
    }
    
    @PostMapping("/create-user")
    public String createUser(@Valid @ModelAttribute("user") User user, HttpSession session, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            return "user-create"; // Returns the form view if there are validation errors
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        Building building = (Building) session.getAttribute("buildingOfLoggedInUser");
        user.setBuilding(building);

        // Save BMS User details
        System.out.println("UserController - POST - createUser - request - user -> " + user);
        try {
            HttpEntity<User> entity = new HttpEntity<>(user, headers);
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users", HttpMethod.POST, entity, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                User newBmsUser = response.getBody();
                System.out.println("UserController - POST - createUser - response - newBmsUser -> " + newBmsUser);
                user = newBmsUser;
                System.out.println("UserController - POST - createUser - response - newBmsUser.getBuilding().getBuildingName() -> " + newBmsUser.getBuilding().getBuildingName());
            } else {
                model.addAttribute("message", "Failed to create BMS User.");
                return "user-create";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error creating BMS User: " + e.getMessage());
            return "user-create";
        }

        model.addAttribute("user", user);
        model.addAttribute("message", "BMS User created successful!");
        return "user-created";
    }

    @GetMapping("/register")
    public String showRegisterPage(HttpServletRequest request, Model model) {
    	
        model.addAttribute("user", new User());
        model.addAttribute("building", new Building());
        model.addAttribute("card", new Card());
        baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        return "register";
        
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, @Valid @ModelAttribute("building") Building building, @Valid @ModelAttribute("card") Card card, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            return "register"; // Returns the form view if there are validation errors
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Save Building Details first
        System.out.println("UserController - POST - register - request - building -> " + building);
        try {
            HttpEntity<Building> entity = new HttpEntity<>(building, headers);
            ResponseEntity<Building> response = restTemplate.exchange(baseUrl+"/api/buildings", HttpMethod.POST, entity, Building.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Building createdBuilding = response.getBody();
                System.out.println("UserController - POST - register - response -> createdBuilding -> " + createdBuilding);
                user.setBuilding(createdBuilding); // Set Building of User
                building = createdBuilding;
            } else {
                model.addAttribute("message", "Failed to create building.");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error creating building: " + e.getMessage());
            return "register";
        }

        // Save Card Details second
        System.out.println("UserController - POST - register - request - card -> " + card);
        try {
            HttpEntity<Card> entity = new HttpEntity<>(card, headers);
            ResponseEntity<Card> response = restTemplate.exchange(baseUrl+"/api/cards", HttpMethod.POST, entity, Card.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Card createdCard = response.getBody();
                System.out.println("UserController - POST - register - response - createdCard -> " + createdCard);
                user.addCard(createdCard); 
                card = createdCard;
            } else {
                model.addAttribute("message", "Failed to create card.");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error creating card: " + e.getMessage());
            return "register";
        }

        System.out.println("UserController - POST - register - request - Building Owner - user -> " + user);
        try {
            HttpEntity<User> entity = new HttpEntity<>(user, headers);
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users", HttpMethod.POST, entity, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                User createdBuildingOwner = response.getBody();
                System.out.println("UserController - POST - register - response - createdBuildingOwner -> " + createdBuildingOwner);
                System.out.println("UserController - POST - register - response -> createdBuildingOwner.getBuilding().getId() -> " + createdBuildingOwner.getBuilding().getId());
                user = createdBuildingOwner;
            } else {
                model.addAttribute("message", "Failed to create Business Owner user.");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error creating Business Owner user: " + e.getMessage());
            return "register";
        }

        model.addAttribute("building", building);
        model.addAttribute("card", card);
        model.addAttribute("user", user);
        model.addAttribute("message", "Registration Successful!");
        return "registered";
        
    }

    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request, Model model) {
    	
        model.addAttribute("user", new User());
        baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        return "login";
        
    }

    @GetMapping("/users")
    public String getAllUsers(HttpSession session, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("UserController - GET - request - users");
        Long buildingId = (Long) session.getAttribute("buildingIdOfLoggedInUser");
        
        try {
            ResponseEntity<List> response = restTemplate.exchange(baseUrl+"/api/users/building/" + buildingId, HttpMethod.GET, null, List.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                List<User> users = response.getBody();
                System.out.println("UserController - GET - response -> users -> " + users);
                model.addAttribute("users", users);
                return "users";
            } else {
                model.addAttribute("message", "Get all users failed.");
                return "index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error getting all users: " + e.getMessage());
            return "index";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model/* , BindingResult result*/) {
        
        // if (result.hasErrors()) {
            // return "login";
        //}

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        System.out.println("UserController - POST - login - request - username -> " + username);
        System.out.println("UserController - POST - login - request - password -> " + password);
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        Building buildingOfLoggerUser = new Building();
        
        try {
            HttpEntity<User> userEntity = new HttpEntity<>(loginUser, headers);
            ResponseEntity<User> responseUser = restTemplate.exchange(baseUrl+"/api/users/login", HttpMethod.POST, userEntity, User.class);
            if (responseUser.getStatusCode().is2xxSuccessful()) {
                User loggedInUser = responseUser.getBody();
                System.out.println("UserController - POST - login - response -> loggedInUser -> " + loggedInUser);
                System.out.println("UserController - POST - login - response -> loggedInUser.getBuilding().getId() -> " + loggedInUser.getBuilding().getId());
                session.setAttribute("loggedInUser", loggedInUser);
                session.setAttribute("buildingIdOfLoggedInUser", loggedInUser.getBuilding().getId()); // Test later
                session.setAttribute("buildingOfLoggedInUser", loggedInUser.getBuilding()); // Test later
                loginUser = loggedInUser;
                buildingOfLoggerUser = loggedInUser.getBuilding();
            } else {
                model.addAttribute("message", "Username and Password are Incorrect");
                return "login";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error in Login: " + e.getMessage());
            return "login";
        }

        model.addAttribute("user", loginUser);
        model.addAttribute("building", buildingOfLoggerUser);
        model.addAttribute("message", "Login Successful!");
        return "logged";
        
    }

    @GetMapping("/user/{id}")
    public String getUserDetails(@PathVariable Long id, Model model) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        User user = new User();
        user.setId(id);
        System.out.println("UserController - GET - getUserDetails - request - user.getId() -> " + user.getId());

        try {

            HttpEntity<User> entity = new HttpEntity<>(user, headers);
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users/"+user.getId(), HttpMethod.GET, entity, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                user = response.getBody();
                System.out.println("UserController - GET - getUserDetails - response -> user -> " + user);
                model.addAttribute("user", user);
            } else {
                model.addAttribute("message", "Failed to Get User Details By Id");
                // return "login";
                return "redirect:/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error in Get User By Id: " + e.getMessage());
            // return "login";
            return "redirect:/index";
        }

        model.addAttribute("message", "Get User Details By Id is Successful!");
        return "user";
        
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session, Model model) {
    	
        session.invalidate();
        model.addAttribute("message", "You have been logged out successfully.");
        System.out.println("UserController - POST - logout");
        return "redirect:/index";
        
    }

}