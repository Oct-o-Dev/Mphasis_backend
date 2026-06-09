package com.mphasis.csp.service;

import com.mphasis.csp.dto.*;
import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.TicketRepository;
import com.mphasis.csp.repository.UserRepository;
import com.mphasis.csp.service.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final TicketRepository ticketDAO;
    private final UserRepository userDAO;

    @Override
    public com.mphasis.csp.dto.TicketResponseDTO raiseTicket(com.mphasis.csp.dto.RaiseTicketRequestDTO dto) {

        User user = userDAO.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = Ticket.builder()
                .user(user)
                .ticketCategory(dto.getCategory())
                .ticketSubcategory(dto.getSubcategory())
                .description(dto.getDescription())
                .build();

        Ticket saved = ticketDAO.save(ticket);
        return mapToDTO(saved);
    }

    @Override
    public com.mphasis.csp.dto.TicketResponseDTO getTicket(GetTicketRequestDTO dto) {

        User user = userDAO.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketDAO
                .findByTicketIdAndUser(dto.getTicketId(), user)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return mapToDTO(ticket);
    }

    @Override
    public List<com.mphasis.csp.dto.TicketResponseDTO> getTickets(GetTicketsRequestDTO dto) {

        User user = userDAO.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketDAO.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private com.mphasis.csp.dto.TicketResponseDTO mapToDTO(Ticket ticket) {
        return com.mphasis.csp.dto.TicketResponseDTO.builder()
                .ticketId(ticket.getTicketId())
                .userId(ticket.getUser().getUserId())
                .category(ticket.getTicketCategory())
                .subcategory(ticket.getTicketSubcategory())
                .description(ticket.getDescription())
                .status(ticket.getTicketStatus())
                .dateOfSubmission(ticket.getDateOfSubmission())
                .dateOfUpdate(ticket.getDateOfUpdate())
                .build();
    }
}