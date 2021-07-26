package com.demo.demo.security.services;

import com.demo.demo.model.ConfirmationToken;
import com.demo.demo.model.Users;
import com.demo.demo.repository.ConfirmationTokenRepository;
import com.demo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;


    public Users findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Users save(Users users){
        return userRepository.save(users);
    }


    public boolean changePassword(String email, String password) {
        Users users = findByEmail (email);
        users.setPassword(passwordEncoder.encode(password));
        if(save(users) != null) {
            return true;
        }
        return false;
    }

    public ConfirmationToken createToken(Users users) {
        ConfirmationToken confirmationToken = new ConfirmationToken(users);
        return confirmationTokenRepository.save(confirmationToken);
    }
    public ConfirmationToken findByConfirmationToken(String token) {
        return confirmationTokenRepository.findByConfirmationToken(token);
    }
    public void deleteToken(ConfirmationToken confirmationToken) {
        this.confirmationTokenRepository.delete(confirmationToken);
    }

}
