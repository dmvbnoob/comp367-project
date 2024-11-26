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
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper; // This is NOT needed

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
        model.addAttribute("message", "Registration successful!");
        return "registered";
    }

    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request, Model model) {
        model.addAttribute("user", new User());
        baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        return "login";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("UserController - GET - request - users");
        try {
            ResponseEntity<List> response = restTemplate.exchange(baseUrl+"/api/users", HttpMethod.GET, null, List.class);
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
        
        try {
            HttpEntity<User> entity = new HttpEntity<>(loginUser, headers);
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users/login", HttpMethod.POST, entity, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                User loggedInUser = response.getBody();
                System.out.println("UserController - POST - login - response -> loggedInUser -> " + loggedInUser);
                System.out.println("UserController - POST - login - response -> loggedInUser.getBuilding().getId() -> " + loggedInUser.getBuilding().getId());
                session.setAttribute("loggedInUser", loggedInUser);
                session.setAttribute("buildingId", loggedInUser.getBuilding().getId()); // Test later
                loginUser = loggedInUser;
            } else {
                model.addAttribute("message", "Username and Password are incorrect");
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error logging in user: " + e.getMessage());
            return "login";
        }

        model.addAttribute("user", loginUser);
        model.addAttribute("message", "Login successful!");
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
                model.addAttribute("message", "Username and Password are incorrect");
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error logging in user: " + e.getMessage());
            return "login";
        }

        model.addAttribute("message", "Get User Details by Id successful!");
        return "user";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("message", "You have been logged out successfully.");
        return "redirect:/index";
    }

}