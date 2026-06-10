package com.mphasis.csp.service;

import com.mphasis.csp.dto.*;
import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.TicketRepository;
import com.mphasis.csp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    @Autowired
    private final TicketRepository ticketRepository;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public TicketResponseDTO raiseTicket(RaiseTicketRequestDTO dto, String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = Ticket.builder()
                .user(user)
                .ticketCategory(dto.getCategory())
                .ticketSubcategory(dto.getSubcategory())
                .description(dto.getDescription())
                .build();

        Ticket saved = ticketRepository.save(ticket);
        return mapToDTO(saved);
    }

    @Override
    public TicketResponseDTO getTicket(GetTicketRequestDTO dto, String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketRepository
                .findByTicketIdAndUser(dto.getTicketId(), user)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return mapToDTO(ticket);
    }

    @Override
    public List<TicketResponseDTO> getTickets(GetTicketsRequestDTO dto, String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDTO> getAllTickets(GetAllTicketsRequestDTO dto) {

        return ticketRepository.findAll(Sort.by("dateOfSubmission").descending())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TicketResponseDTO mapToDTO(Ticket ticket) {
        return TicketResponseDTO.builder()
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