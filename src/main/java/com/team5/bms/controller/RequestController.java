package com.team5.bms.controller;
 
import jakarta.validation.Valid;
import com.team5.bms.model.Card;
import com.team5.bms.model.Request;
import com.team5.bms.model.User;
import com.team5.bms.model.enumeration.Priorities;
import com.team5.bms.model.enumeration.Roles;
import com.team5.bms.model.enumeration.Statuses;
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
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 
 * RequestController.java
 *
 * @author Jasper Belenzo
 * @author Jophiel Serrano
 * 
 */
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
        	requestUrl = baseUrl + "/api/requests/building/" + buildingIdOfLoggedInUser + "/assigned/" + loggedInUser.getId();
        }
        // if (roleOfLoggedInUser.equals(Roles.OWNER.name()) || roleOfLoggedInUser.equals(Roles.ADMINISTRATOR.name())) {
        try {
        	ResponseEntity<List> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, List.class);
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
    
    
    @GetMapping("/edit-request/{id}")
    public String showEditRequestPage(@PathVariable("id") Long id, HttpServletRequest request, HttpSession session, Model model) {
    	
    	baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
    	Building buildingOfLoggedInUser = (Building) session.getAttribute("buildingOfLoggedInUser");
    	
    	/** START - Get List of SuperIntendents */
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        try {
            ResponseEntity<List> response = restTemplate.exchange(baseUrl+"/api/users/building/" + buildingOfLoggedInUser.getId() + "/role/" + Roles.SUPERINTENDENT, HttpMethod.GET, null, List.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                List<Request> listOfSuperIntendents = response.getBody();
                System.out.println("RequestController - GET - findUsersByRole -> listOfSuperIntendents -> " + listOfSuperIntendents);
                model.addAttribute("message", "Get List of SuperIntendent Users successful!");
                model.addAttribute("superintendents", listOfSuperIntendents);
            } else {
                model.addAttribute("message", "Get Requests failed.");
                return "requests";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error on Get Requests " + e.getMessage());
            return "requests";
        }
        /** END Get List of SuperIntendents */
    
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("RequestController - GET - showEditRequestPage - id -> " + id);
        try {
            ResponseEntity<Request> response = restTemplate.exchange(baseUrl+"/api/requests/" + id, HttpMethod.GET, null, Request.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Request requestToBeEdited = response.getBody();
                System.out.println("UserController - GET - showEditRequestPage -> requestToBeEdited -> " + requestToBeEdited);
                model.addAttribute("request", request);
                // List<Roles> roles = Arrays.asList(Roles.values());
                List<Priorities> priorities = Arrays.stream(Priorities.values())
                        //.filter(priority -> priority != Priorities.OWNER)
                        .collect(Collectors.toList());
                model.addAttribute("priorities", priorities);
                model.addAttribute("request", requestToBeEdited);
                return "request-edit";
            } else {
                model.addAttribute("message", "Get Request By Id failed.");
                return "requests";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error on Get Request By Id: " + e.getMessage());
            return "requests";
        }
              
    }
    
    @PostMapping("/edit-request")
    public String editRequest(@Valid @ModelAttribute("request") Request request, String requestUserId, HttpSession session, BindingResult result, Model model) {
    	
    	System.out.println("RequestController - POST - editRequest - requestUserId -> " + requestUserId);
        if (result.hasErrors()) {
            return "request-edit"; // Returns the form view if there are validation errors
        }
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        System.out.println("RequestController - POST - editRequest - loggedInUser.getRole() -> " + loggedInUser.getRole());
   
        /** START - Get the User (Tenant) who owns (created) the Request */
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        User userWithTheRequest = new User();
        userWithTheRequest.setId(Long.valueOf(requestUserId));
        try {
            HttpEntity<User> entity = new HttpEntity<>(userWithTheRequest, headers);
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users/"+userWithTheRequest.getId(), HttpMethod.GET, entity, User.class);
            if (response.getStatusCode().is2xxSuccessful()) {
            	userWithTheRequest = response.getBody();
                System.out.println("UserController - GET - editRequest - response -> userWithTheRequest -> " + userWithTheRequest);
            } else {
                model.addAttribute("message", "Failed to Get User Details By Id");
                return "request-edit";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error in Get User By Id: " + e.getMessage());
            return "request-edit";
        }
        /** END - Get the User (Tenant) who owns (created) the Request */
        
        /** START - Get the User (SuperIntendent) who is Assigned to the Request */
        if (request.getAssigneeId() != null) {
	        headers = new HttpHeaders();
	        headers.set("Content-Type", "application/json");
	        User assignedSuperIntendent = new User();
	        assignedSuperIntendent.setId(Long.valueOf(request.getAssigneeId()));
	        try {
	            HttpEntity<User> entity = new HttpEntity<>(assignedSuperIntendent, headers);
	            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"/api/users/"+ assignedSuperIntendent.getId(), HttpMethod.GET, entity, User.class);
	            if (response.getStatusCode().is2xxSuccessful()) {
	            	assignedSuperIntendent = response.getBody();
	                System.out.println("UserController - GET - editRequest - response -> assignedSuperIntendent -> " + assignedSuperIntendent);
	                request.setAssignee(assignedSuperIntendent.getUsername());
	                request.setAssigneeId(assignedSuperIntendent.getId());
	                if ((loggedInUser.getRole() == Roles.OWNER) || (loggedInUser.getRole() == Roles.ADMINISTRATOR)){
	                	System.out.println("OWNER and ADMIN can only ASSIGN or RE-ASSIGN");
	                	request.setAssignDate(Instant.now());
	                	request.setStatus(Statuses.ASSIGNED);
	                }
	                if ((loggedInUser.getRole() == Roles.SUPERINTENDENT) && (request.getStatus() == Statuses.ASSIGNED)){
	                	System.out.println("SUPERINTENDENT can only transition from ASSIGNED to IN_PROGRESS");
	                	request.progressDate(Instant.now());
	                	request.setStatus(Statuses.IN_PROGRESS);
	                } else if ((loggedInUser.getRole() == Roles.SUPERINTENDENT) && (request.getStatus() == Statuses.IN_PROGRESS)){
	                	System.out.println("SUPERINTENDENT can =transition Status from IN_PROGRESS to COMPLETED");
	                	request.setUpdateDate(Instant.now());
	                	request.setStatus(Statuses.COMPLETED);
	                } else if ((loggedInUser.getRole() == Roles.SUPERINTENDENT) && (request.getStatus() == Statuses.COMPLETED)){
	                	System.out.println("SUPERINTENDENT can transition status from COMPLETED to IN_PROGRESS"); // Assuming SUPER made a mistake and is still not finish (or case of rework).
	                	request.setUpdateDate(Instant.now());
	                	request.progressDate(Instant.now());
	                	request.setStatus(Statuses.IN_PROGRESS);
	                }
	                
	            } else {
	                model.addAttribute("message", "Failed to Get User Details By Id");
	                return "request-edit";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            model.addAttribute("message", "Error in Get User By Id: " + e.getMessage());
	            return "request-edit";
	        }
        } // request.getAssigneeId() != null
        /** END - Get the User (Tenant) who owns (created) the Request */
        
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("RequestController - POST - editRequest - request -> " + request);
        request.setUser(userWithTheRequest);
        request.setUpdateDate(Instant.now());
        try {
        	HttpEntity<Request> entity = new HttpEntity<>(request, headers);
        	ResponseEntity<Request> response = restTemplate.exchange(baseUrl+"/api/requests/" + request.getId(), HttpMethod.PUT, entity, Request.class);
        	if (response.getStatusCode().is2xxSuccessful()) {
        		Request updatedRequest = response.getBody();
        		System.out.println("UserController - POST - editUser - response - updatedRequest -> " + updatedRequest);
        		request = updatedRequest;
        	} else {
        		model.addAttribute("message", "Failed to Edit BMS Request.");
        		return "request-edit";
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        	model.addAttribute("message", "Error in Edit BMS Request: " + e.getMessage());
        	return "request-edit";
        }
        model.addAttribute("request", request);
        model.addAttribute("message", "BMS Request Edit successful!");
        return "request-edited";
    }
    
    @GetMapping("/create-request")
    public String showCreateRequestPage(HttpServletRequest request, Model model) {
    	
        model.addAttribute("request", new Request());
        baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        List<Priorities> priorities = Arrays.asList(Priorities.values());
        /* List<Roles> roles = Arrays.stream(Roles.values())
                .filter(role -> role != Roles.OWNER)
                .collect(Collectors.toList()); */
        model.addAttribute("priorities", priorities);
        return "request-create";
        
    }
    
    @PostMapping("/create-request")
    public String createRequest(@Valid @ModelAttribute("request") Request request, HttpSession session, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            return "request-create"; // Returns the form view if there are validation errors
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        // Building building = (Building) session.getAttribute("buildingOfLoggedInUser");
        request.setCreateDate(Instant.now());
        request.setStatus(Statuses.CREATED);
        request.setUpdateDate(Instant.now());
        User loggedInUser = (User) session.getAttribute("loggedInUser"); // Should be Tenant, Owner or Administrator
        request.setUser(loggedInUser);

        // Save BMS Request details
        System.out.println("RequestController - POST - createRequest - request -> " + request);
        try {
            HttpEntity<Request> entity = new HttpEntity<>(request, headers);
            ResponseEntity<Request> response = restTemplate.exchange(baseUrl+"/api/requests", HttpMethod.POST, entity, Request.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Request newlyCreatedRequest = response.getBody();
                System.out.println("UserController - POST - createRequest - response - newlyCreatedRequest  -> " + newlyCreatedRequest);
                request = newlyCreatedRequest ;
                System.out.println("UserController - POST - createUser - response - newlyCreatedRequest.getId() -> " + newlyCreatedRequest.getId() + ", newlyCreatedRequest.getTitle() -> " + newlyCreatedRequest.getTitle());
            } else {
                model.addAttribute("message", "Failed to create BMS Request.");
                return "request-create";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error creating BMS Request: " + e.getMessage());
            return "request-create";
        }

        model.addAttribute("request", request);
        model.addAttribute("message", "BMS Request created successful!");
        return "request-created";
        
    }

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
                return "redirect:/index";
            }
        } catch (Exception e) {
        	
            e.printStackTrace();
            model.addAttribute("message", "Error getting all users: " + e.getMessage());
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
            ResponseEntity<Request> response = restTemplate.exchange(baseUrl+"/api/requests/"+request.getId(), HttpMethod.GET, entity, Request.class);
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