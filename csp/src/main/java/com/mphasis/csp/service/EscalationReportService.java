package com.mphasis.csp.service;

import com.mphasis.csp.model.EscalationReport;
import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.EscalationReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EscalationReportService {

    private final EscalationReportRepository repository;

    public void saveEscalation(Ticket ticket, User user) {

        // avoid duplicate insert within short interval
        Optional<EscalationReport> last =
                repository.findTopByTicketOrderByDateOfEscalationDesc(ticket);

        if (last.isPresent() &&
                last.get().getDateOfEscalation()
                        .isAfter(LocalDateTime.now().minusMinutes(1))) {
            return;
        }

        EscalationReport report =
                EscalationReport.builder()
                        .ticket(ticket)
                        .user(user)
                        .dateOfEscalation(LocalDateTime.now())
                        .build();

        repository.save(report);
    }
}