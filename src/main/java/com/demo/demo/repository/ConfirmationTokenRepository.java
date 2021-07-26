package com.demo.demo.repository;

import com.demo.demo.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository 
            extends JpaRepository<ConfirmationToken, Long> {
    
    ConfirmationToken findByConfirmationToken(String token);
}