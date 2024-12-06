package com.team5.bms.controller;
 
import jakarta.validation.Valid;
import com.team5.bms.model.Card;
import com.team5.bms.model.Request;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RequestController {
	
    private static final Logger LOG = LoggerFactory.getLogger(RequestController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper; // This is NOT needed

    private String baseUrl;
    
    @GetMapping("/requests")
    public String getRequests(HttpSession session, Model model) {
    	
    	baseUrl = (String) session.getAttribute("baseUrl");
    	User loggedInUser = (User) session.getAttribute("loggedInUser");
    	String roleOfLoggedInUser = loggedInUser.getRole().name();
    	LOG.info("RequestController - GET - getRequests -> roleOfLoggedInUser -> " + roleOfLoggedInUser);
        Long buildingIdOfLoggedInUser = (Long) session.getAttribute("buildingIdOfLoggedInUser");
        LOG.info("RequestController - GET - getRequests -> buildingIdOfLoggedInUser -> " + buildingIdOfLoggedInUser);
        Building buildingOfLoggedInUser = (Building) session.getAttribute("buildingOfLoggedInUser");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String requestUrl = "";
        
        if (roleOfLoggedInUser.equals(Roles.OWNER.name()) || roleOfLoggedInUser.equals(Roles.ADMINISTRATOR.name())) {
        	requestUrl = baseUrl + "/api/requests/building/" + buildingIdOfLoggedInUser;
        } else if (roleOfLoggedInUser.equals(Roles.TENANT.name())) {
        	requestUrl = baseUrl + "/api/requests/building/" + buildingIdOfLoggedInUser + "/user/" + loggedInUser.getId();
        } else {
        	// SUPERINTENDENT
        }
        
        // if (roleOfLoggedInUser.equals(Roles.OWNER.name()) || roleOfLoggedInUser.equals(Roles.ADMINISTRATOR.name())) {
            try {
                ResponseEntity<List> response = restTemplate.exchange(baseUrl+"/api/requests/building/" + buildingIdOfLoggedInUser, HttpMethod.GET, null, List.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    List<Request> requests = response.getBody();
                    System.out.println("RequestController - GET - getRequests -> requests -> " + requests);
                    model.addAttribute("message", "Get Requests successful!");
                    model.addAttribute("requests", requests);
                    model.addAttribute("user", loggedInUser);
                    model.addAttribute("building", buildingOfLoggedInUser);
                    return "requests";
                } else {
                    model.addAttribute("message", "Get Requests failed.");
                    return "redirect:/index";
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("message", "Error on Get Requests " + e.getMessage());
                return "redirect:/index";
            }
        // } 
        
        /* if (roleOfLoggedInUser.equals(Roles.TENANT.name())) {
            try {
                ResponseEntity<List> response = restTemplate.exchange(baseUrl+"/api/requests/building/" + buildingIdOfLoggedInUser + "/user/" + loggedInUser.getId(), HttpMethod.GET, null, List.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    List<Request> requests = response.getBody();
                    System.out.println("RequestController - GET - getRequests -> requests -> " + requests);
                    model.addAttribute("message", "Get Requests successful!");
                    model.addAttribute("requests", requests);
                    model.addAttribute("user", loggedInUser);
                    model.addAttribute("building", buildingOfLoggedInUser);
                    return "requests";
                } else {
                    model.addAttribute("message", "Get Requests failed.");
                    return "redirect:/index";
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("message", "Error on Get Requests " + e.getMessage());
                return "redirect:/index";
            }
        } */
        
        // return "requests";
    }
    
    /** @GetMapping("/delete-user/{id}")
    public String showDeleteUserPage(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
    	
    	baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("UserController - GET - showDeleteUserPage - id" + id);

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
              
    } **/
    
    /** @PostMapping("/delete-user")
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
        
    } **/
    
    
    /** @GetMapping("/edit-user/{id}")
    public String showEditUserPage(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
    	
    	baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("UserController - GET - showEditUserPage - id -> " + id);

        try {
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users/" + id, HttpMethod.GET, null, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                User user = response.getBody();
                System.out.println("UserController - GET - showEditUserPage -> user -> " + user);
                model.addAttribute("user", user);
                // List<Roles> roles = Arrays.asList(Roles.values());
                List<Roles> roles = Arrays.stream(Roles.values())
                        .filter(role -> role != Roles.OWNER)
                        .collect(Collectors.toList());
                model.addAttribute("roles", roles);
                return "user-edit";
            } else {
                model.addAttribute("message", "Get User By Id failed.");
                return "users";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error on Get User By Id: " + e.getMessage());
            return "users";
        }
              
    } **/
    
    /** @PostMapping("/edit-user")
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
    } **/
    
    /** @GetMapping("/create-user")
    public String showCreateUserPage(HttpServletRequest request, Model model) {
    	
        model.addAttribute("user", new User());
        baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        // List<Roles> roles = Arrays.asList(Roles.values());
        List<Roles> roles = Arrays.stream(Roles.values())
                .filter(role -> role != Roles.OWNER)
                .collect(Collectors.toList());
        model.addAttribute("roles", roles);
        return "user-create";
        
    } **/
    
    /** @PostMapping("/create-user")
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
        
    } **/

    @GetMapping("/requests/all")
    public String getAllRequests(HttpSession session, Model model) {
    	
    	baseUrl = (String) session.getAttribute("baseUrl");
    	LOG.info("baseURL -> " + baseUrl);
    	// baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("RequestController - GET - getAllRequests");
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Building buildingOfLoggedInUser = (Building) session.getAttribute("buildingOfLoggedInUser");
        Long buildingIdOfLoggedInUser = (Long) session.getAttribute("buildingIdOfLoggedInUser");    		
        
        try {
        	
            ResponseEntity<List> response = restTemplate.exchange(baseUrl+"/api/requests", HttpMethod.GET, null, List.class);
            if (response.getStatusCode().is2xxSuccessful()) {
            	
                List<Request> requests = response.getBody();
                System.out.println("RequestController - GET - getAllRequests -> requests -> " + requests);
                model.addAttribute("message", "All Requests retrieved successfully");
                model.addAttribute("requests", requests);
                model.addAttribute("user", loggedInUser);
                model.addAttribute("building", buildingOfLoggedInUser);
                return "requests";
                
            } else {
            	
                model.addAttribute("message", "Get all users failed.");
                // return "index";
                return "redirect:/index";
                
            }
        } catch (Exception e) {
        	
            e.printStackTrace();
            model.addAttribute("message", "Error getting all users: " + e.getMessage());
            // return "index";
            return "redirect:/index";
            
        }
    }


    @GetMapping("/request/{id}")
    public String getRequestDetails(@PathVariable Long id, Model model) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        Request request = new Request();
        request.setId(id);
        System.out.println("RequestController - GET - getRequestDetails- request.getId() -> " + request.getId());

        try {

            HttpEntity<Request> entity = new HttpEntity<>(request, headers);
            ResponseEntity<Request> response = restTemplate.exchange(baseUrl+"/api/users/"+request.getId(), HttpMethod.GET, entity, Request.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                request = response.getBody();
                System.out.println("UserController - GET - getRequestDetails -> request -> " + request);
                model.addAttribute("request", request);
            } else {
            	
                model.addAttribute("message", "Failed to Get Request Details By Id");
                return "redirect:/index";
                
            }
        } catch (Exception e) {
        	
            e.printStackTrace();
            model.addAttribute("message", "Error in Get Request By Id: " + e.getMessage());
            return "redirect:/index";
            
        }

        model.addAttribute("message", "Get Request Details By Id is Successful!");
        return "request";
        
    }

}