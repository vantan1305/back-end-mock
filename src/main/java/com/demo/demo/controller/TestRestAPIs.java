package com.demo.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class TestRestAPIs {

//    @GetMapping("/all")
//    public String allAccess(){
//        return "OK";
//    }

    @GetMapping("/api/role/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER') or hasRole('PM')")
    public String userAccess() {
        return ">>> User Contents!";
    }

    @GetMapping("/api/role/pm")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('MANAGER')")
    public String projectManagementAccess() {
        return ">>> PM Contents";
    }

    @GetMapping("/api/role/manager")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public String projectManagerAccess() {
        return ">>> Project Management Board";
    }

    @GetMapping("/api/role/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return ">>> Admin Contents";
    }
}
