package com.demo.demo.controller;


import com.demo.demo.repository.UserRepository;
import com.demo.demo.security.services.UserDetailsServiceImpl;
import com.demo.demo.security.services.iservice.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/role/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/user/me")
//    @PreAuthorize("hasRole('USER')")
//    public Users getCurrentUser(@CurrentUser UserPrinciple userPrinciple) {
//        return userRepository.findById(userPrinciple.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrinciple.getId()));
//    }

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    ServletContext context;

    @Autowired
    private ImageService imageService;


    @GetMapping("/all")
    public ResponseEntity findAll(){
        return ResponseEntity.ok().body(userDetailsService.loadAll ());
    }

    @GetMapping("/{userName}")
    public ResponseEntity fineByName(@PathVariable String  userName) {
        return  ResponseEntity.ok (  ).body (userDetailsService.loadUserByUsername (userName));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id){
        return ResponseEntity.ok (  ).body (userDetailsService.loadUserById (id));
    }

    @GetMapping("/{email}")
    public ResponseEntity findByEmail(@PathVariable String email){
        return ResponseEntity.ok (  ).body (userDetailsService.loadUserByEmail (email));
    }

}
