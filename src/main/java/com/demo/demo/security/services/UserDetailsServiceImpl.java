package com.demo.demo.security.services;


import com.demo.demo.exception.ResourceNotFoundException;
import com.demo.demo.model.Users;
import com.demo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ServletContext context;
//    @Autowired
//    UserDAO userDAO;

    public List<Users> loadAll() {
        System.out.println ("Get all Users...");
        return userRepository.findAll (Sort.by ("userName").ascending ( ));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users users = userRepository.findByUserName (userName).orElseThrow (
                () -> new UsernameNotFoundException ("User Not Found with -> username or email : " + userName));
        return UserPrinciple.build (users);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Users users = userRepository.findById (id).orElseThrow (
                () -> new ResourceNotFoundException ("Users", "id", id)
        );

        return UserPrinciple.build (users);
    }

    @Transactional
    public UserDetails loadUserByEmail(String email) {
        Users users = userRepository.findByEmail (email).orElseThrow (
                () -> new ResourceNotFoundException ("Users", "email", email)
        );
        return UserPrinciple.build (users);
    }

    @Transactional
    public boolean isAccountVerified(String email) {
        boolean isVerified = userRepository.findEmailVerifiedByEmail (email);
        return isVerified;
    }


}
