package com.mphasis.csp.repository;

import com.mphasis.csp.model.TicketService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketServiceRepository extends JpaRepository<TicketService, Integer> {

    @Query("SELECT s FROM TicketService s WHERE s.ticket.ticketId = :pTicketId ORDER BY s.dateOfService")
    List<TicketService> findTicketServiceHistory(@Param("pTicketId") Integer ticketId);
}