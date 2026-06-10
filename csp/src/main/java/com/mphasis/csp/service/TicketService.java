package com.mphasis.csp.service;

import com.mphasis.csp.dto.*;
import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.TicketRepository;
import com.mphasis.csp.repository.UserRepository;
//import com.mphasis.csp.model.TicketService;
import com.mphasis.csp.repository.TicketServiceRepository;
import com.mphasis.csp.enums.TicketStatus;
import com.mphasis.csp.dto.RaiseServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    @Autowired
    private final TicketRepository ticketRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TicketServiceRepository ticketServiceRepository;

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

    @Override
    public TicketResponseDTO raiseService(RaiseServiceDTO dto) {

        //  Step 1: Ticket fetch
        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        //  Step 2: Old status
        TicketStatus oldStatus = ticket.getTicketStatus();

        // Step 3: Create new service record
        com.mphasis.csp.model.TicketService service =
                new com.mphasis.csp.model.TicketService();


        service.setTicket(ticket);
        service.setServiceAction(dto.getServiceType());
        service.setComment(dto.getComment());
        service.setOldStatus(oldStatus);
        service.setNewStatus(dto.getNewStatus());
        service.setDateOfService(LocalDateTime.now());

        //  Step 4: CRO fetch
        User cro = userRepository.findById(dto.getCroId().longValue())
                .orElseThrow(() -> new RuntimeException("CRO not found"));

        service.setCro(cro);

        //  Step 5: SAVE into services table
        ticketServiceRepository.save(service);

        // Step 6: Update ticket status
        ticket.setTicketStatus(dto.getNewStatus());

        Ticket updated = ticketRepository.save(ticket);

        return mapToDTO(updated);
    }

}