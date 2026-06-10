package com.mphasis.csp.repository;

import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByUser(User user);

    Optional<Ticket> findByTicketIdAndUser(Integer ticketId, User user);
}