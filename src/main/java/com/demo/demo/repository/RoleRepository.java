package com.demo.demo.repository;

import com.demo.demo.model.Role;
import com.demo.demo.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(RoleName name);
}
