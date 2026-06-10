package com.mphasis.csp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.mphasis.csp.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailId(String emailId);
}