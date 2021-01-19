package com.teamgorm.projectforum.controller;

import com.teamgorm.projectforum.model.User;
import com.teamgorm.projectforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Auth Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    private Principal principal;

    /**
     * Create a new user
     *
     * @param user The user created
     * @return
     */
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * Delete a user by id
     * This action must be taken by ADMIN role
     * @param id
     */

    @PreAuthorize("hasRole('ROLE_ADMIN') or @authController.getId() == #id")
    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable String id) {
        userService.deleteById(id);
    }

    public String getId(){
        Object currentPrinciple = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) currentPrinciple).getUsername();
        return userService.getByName(username).getId();
    }
}
