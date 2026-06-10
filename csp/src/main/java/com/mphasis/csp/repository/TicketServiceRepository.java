package com.mphasis.csp.repository;

import com.mphasis.csp.model.TicketService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketServiceRepository extends JpaRepository<TicketService, Integer> {
}