package com.mphasis.csp.repository;
import com.mphasis.csp.dto.response.CroDashboardResponseDTO;
import com.mphasis.csp.enums.TicketStatus;
import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByUser(User user);

    Optional<Ticket> findByTicketIdAndUser(Integer ticketId, User user);
    List<Ticket> findByTicketStatus(TicketStatus status);

    @Query("SELECT new com.mphasis.csp.dto.response.CroDashboardResponseDTO(" +
        "ts.cro.id, " +
        "ts.cro.username, " +

        "COUNT(ts), " +

        "SUM(CASE WHEN ts.newStatus = 'PENDING_CRO' THEN 1 ELSE 0 END), " +
        "SUM(CASE WHEN ts.newStatus = 'PENDING_MANAGER' THEN 1 ELSE 0 END), " +
        "SUM(CASE WHEN ts.newStatus = 'CLOSED_RESOLVED' THEN 1 ELSE 0 END), " +
        "SUM(CASE WHEN ts.newStatus = 'CLOSED_REJECTED' THEN 1 ELSE 0 END)) " +

        "FROM TicketService ts " +
        "WHERE ts.cro.role = 'CRO' " +
        "GROUP BY ts.cro.id, ts.cro.username")
    List<CroDashboardResponseDTO> getCroDashboard();

    List<Ticket> findByTicketStatusIn(List<TicketStatus> statuses);

    //NEW METHOD FOR LOAD BALANCING
    long countByAssignedTo(User user);

    @Query("SELECT t FROM Ticket t WHERE t.assignedTo.emailId = :email")
    List<Ticket> findTicketsByAssignedUserEmail(@Param("email") String email);}