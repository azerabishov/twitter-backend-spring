package org.azerabshv.controllers;


import org.azerabshv.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1")
public class UserController {
    @GetMapping("/get")
    public long loginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        return userPrincipal.getId();
    }

}
