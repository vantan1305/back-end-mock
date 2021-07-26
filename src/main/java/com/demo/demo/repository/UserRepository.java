package com.demo.demo.repository;

import com.demo.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Component
public interface UserRepository extends JpaRepository <Users, Long>{
    Optional<Users> findByUserName(String userName);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);
    @Query("SELECT u.emailVerified FROM Users u WHERE u.email = ?1")
    Boolean findEmailVerifiedByEmail(String email);

//    Users getUsers(long id);
//
//    public int updateAvatar(String avatar , long id);
}
