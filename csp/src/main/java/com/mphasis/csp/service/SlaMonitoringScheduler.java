package com.mphasis.csp.service;

import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.enums.TicketStatus;
import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.TicketService;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SlaMonitoringScheduler {

    private final TicketRepository ticketRepository;
    private final TicketServiceService ticketServiceService;
    private final EscalationReportService escalationReportService;

    @Scheduled(fixedRate = 600000) // 10 minutes
    public void checkSlaAndEscalate() {

        System.out.println("SLA Scheduler Running...");

        List<Ticket> tickets = ticketRepository.findByTicketStatusIn(
                List.of(
                        TicketStatus.PENDING_CUSTOMER,
                        TicketStatus.PENDING_CRO
                )
        );

        for (Ticket ticket : tickets) {

            int slaMinutes = ticket.getTicketCategory().getSLAMinutes();

            LocalDateTime createdTime = ticket.getDateOfSubmission();

            long minutesElapsed =
                    java.time.Duration.between(createdTime, LocalDateTime.now()).toMinutes();

            if (minutesElapsed >= slaMinutes) {

                ServiceAction nextAction = getEscalationAction(ticket.getTicketStatus());

                if (nextAction == null) { continue; }

                // use existing service logic (VERY IMPORTANT)
                TicketService escalationService = ticketServiceService.applySystemAction(ticket, nextAction);

                System.out.println(
                        "🚨 Escalated Ticket ID: " + ticket.getTicketId()
                );

                // Audit LOG Insert
                //Who triggered escalation -> system or assigned user
                User user=ticket.getAssignedTo() != null
                          ? ticket.getAssignedTo()
                          :ticket.getUser();

                escalationReportService.saveEscalation(escalationService, ticket, user);
            }
        }
    }

    private ServiceAction getEscalationAction(TicketStatus status) {

        return switch (status) {

            case PENDING_CUSTOMER -> ServiceAction.ESCALATE_TO_CRO;

            case PENDING_CRO -> ServiceAction.ESCALATE_TO_MANAGER;

            case PENDING_MANAGER -> null; //  no further escalation

            default -> null;
        };
    }
}