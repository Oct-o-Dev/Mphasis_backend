package com.mphasis.csp.repository;

import com.mphasis.csp.model.EscalationReport;
import com.mphasis.csp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EscalationReportRepository
        extends JpaRepository<EscalationReport, Long> {

    Optional<EscalationReport>
    findTopByTicketOrderByDateOfEscalationDesc(Ticket ticket);
}
