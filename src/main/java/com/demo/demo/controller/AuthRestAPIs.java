package com.demo.demo.controller;

import com.demo.demo.exception.BadRequestException;
import com.demo.demo.message.request.LoginForm;
import com.demo.demo.message.request.SignUpForm;
import com.demo.demo.message.response.ApiResponse;
import com.demo.demo.message.response.JwtResponse;
import com.demo.demo.message.response.ResponseMessage;
import com.demo.demo.message.response.VerifyEmailRequest;
import com.demo.demo.model.ConfirmationToken;
import com.demo.demo.model.Role;
import com.demo.demo.model.RoleName;
import com.demo.demo.model.Users;
import com.demo.demo.repository.RoleRepository;
import com.demo.demo.repository.UserRepository;
import com.demo.demo.security.jwt.JwtProvider;
import com.demo.demo.security.services.AuthService;
import com.demo.demo.security.services.EmailSenderService;
import com.demo.demo.security.services.UserDetailsServiceImpl;
import com.demo.demo.security.services.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthService authService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    EmailSenderService emailSenderService;


    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

            UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
//            them moi
        List<String> roles = userDetails.getAuthorities ().stream ()
                .map (item -> item.getAuthority ())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername (), userDetails.getAuthorities(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUserName (signUpRequest.getUserName())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        Users users = new Users(
                                signUpRequest.getUserName(),
                                signUpRequest.getEmail(),
                                encoder.encode(signUpRequest.getPassword()));
        ConfirmationToken confirmationToken = authService.createToken(users);
        emailSenderService.sendMail(users.getEmail(), confirmationToken.getConfirmationToken());


        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "manager":
                        Role managerRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(managerRole);

                        break;
                    case "pm":
                        Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(pmRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        users.setRoles(roles);
        userRepository.save(users);

        return new ResponseEntity(new ResponseMessage("User registered successfully!" ), HttpStatus.OK);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> getMethodName(@RequestParam("token") String token) {

        ConfirmationToken confirmationToken = authService.findByConfirmationToken(token);

        if (confirmationToken == null) {
            throw new BadRequestException("Invalid token");
        }

        Users user = confirmationToken.getUsers ();
        Calendar calendar = Calendar.getInstance();

        if((confirmationToken.getExpiryDate().getTime() -
                calendar.getTime().getTime()) <= 0) {
            return ResponseEntity.badRequest().body("Link expired. Generate new link from http://localhost:4200/login");
        }

        user.setEmailVerified(true);
        authService.save(user);
        return ResponseEntity.ok("Account verified successfully!");
    }


    @PostMapping("/send-email")
    public ResponseEntity<?> sendVerificationMail(@Valid @RequestBody
                                                          VerifyEmailRequest emailRequest) {
        if(authService.existsByEmail(emailRequest.getEmail())){
            if(userDetailsService.isAccountVerified(emailRequest.getEmail())){
                throw new BadRequestException("Email is already verified");
            } else {
                Users users = authService.findByEmail(emailRequest.getEmail());
                ConfirmationToken token = authService.createToken(users);
                emailSenderService.sendMail(users.getEmail(), token.getConfirmationToken());
                return ResponseEntity.ok(new ApiResponse (true, "Verification link is sent on your mail id"));
            }
        } else {
            throw new BadRequestException("Email is not associated with any account");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody LoginForm loginForm) {
        if(authService.existsByEmail(loginForm.getEmail ())){
            if(authService.changePassword(loginForm.getEmail (), loginForm.getPassword())) {
                return ResponseEntity.ok(new ApiResponse (true, "Password changed successfully"));
            } else {
                throw new BadRequestException("Unable to change password. Try again!");
            }
        } else {
            throw new BadRequestException("User not found with this email id");
        }
    }

}
