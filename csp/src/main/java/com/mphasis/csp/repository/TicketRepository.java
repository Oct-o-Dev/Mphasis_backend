package com.mphasis.csp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.mphasis.csp.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}