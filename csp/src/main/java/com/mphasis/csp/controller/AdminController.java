package com.mphasis.csp.controller;



import com.mphasis.csp.dto.response.CroDashboardResponseDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;
import com.mphasis.csp.enums.TicketStatus;
import com.mphasis.csp.service.ITicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ITicketService ticketService;

    // Get Pending Tickets (Customer)
    @GetMapping("/getPending")
    public List<TicketResponseDTO> getPending() {
        return ticketService.getTicketsByStatus(TicketStatus.PENDING_CUSTOMER.name());
    }

    // Get Active Tickets (CRO)
    @GetMapping("/getActive")
    public List<TicketResponseDTO> getActive() {
        return ticketService.getTicketsByStatus(TicketStatus.PENDING_CRO.name());
    }

    //  Get Escalated Tickets (Manager)
    @GetMapping("/getEscalated")
    public List<TicketResponseDTO> getEscalated() {
        return ticketService.getTicketsByStatus(TicketStatus.PENDING_MANAGER.name());
    }

    // Get Resolved Tickets
    @GetMapping("/getResolved")
    public List<TicketResponseDTO> getResolved() {
        return ticketService.getTicketsByStatus(TicketStatus.CLOSED_RESOLVED.name());
    }

    //  Get Rejected Tickets
    @GetMapping("/getRejected")
    public List<TicketResponseDTO> getRejected() {
        return ticketService.getTicketsByStatus(TicketStatus.CLOSED_REJECTED.name());
    }

    @GetMapping("/cro-dashboard")
    public List<CroDashboardResponseDTO> getCroDashboard() {
        return ticketService.getCroDashboard();
    }
}