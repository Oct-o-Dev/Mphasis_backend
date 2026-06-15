package com.mphasis.csp.service;

import com.mphasis.csp.dto.request.*;
import com.mphasis.csp.dto.response.CroDashboardResponseDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;
import com.mphasis.csp.enums.TicketCategory;
import com.mphasis.csp.exception.DebitCardNotFoundException;
import com.mphasis.csp.exception.UserNotFoundException;
import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.DebitCardRepository;
import com.mphasis.csp.repository.TicketRepository;
import com.mphasis.csp.repository.UserRepository;
import com.mphasis.csp.repository.TicketServiceRepository;
import com.mphasis.csp.util.MapToTicketResponseDTO;
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

    @Autowired
    private final TicketServiceRepository ticketServiceRepository;

    @Autowired
    private final DebitCardRepository debitCardRepository;

    @Override
    public TicketResponseDTO raiseTicket(RaiseTicketRequestDTO dto, String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new UserNotFoundException("User is not found"));

        // If it's a debit card request, validate that Debit Card belongs to user
        String debitCardLast4Digits = dto.getDebitCardLast4Digits();
        if (
                dto.getCategory() == TicketCategory.DEBITCARDSERVICE &&
                debitCardRepository.findByUser(user)
                        .stream()
                        .noneMatch(debitCard -> {
                            String number = debitCard.getDebitCardNumber();
                            return number != null &&
                                    number.length() >= 4 &&
                                    number.substring(number.length() - 4)
                                            .equals(debitCardLast4Digits);
                })) {
            throw new DebitCardNotFoundException(user, debitCardLast4Digits);
        }

        // If everything is verified, create ticket and send to DB
        Ticket ticket = Ticket.builder()
                .user(user)
                .ticketCategory(dto.getCategory())
                .ticketSubcategory(dto.getSubcategory())
                .description(dto.getDescription())
                .build();

        Ticket saved = ticketRepository.save(ticket);
        return MapToTicketResponseDTO.map(saved);
    }

    @Override
    public TicketResponseDTO getTicket(GetTicketRequestDTO dto, String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketRepository
                .findByTicketIdAndUser(dto.getTicketId(), user)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return MapToTicketResponseDTO.map(ticket);
    }

    @Override
    public List<TicketResponseDTO> getTickets(String requestDTO, String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketRepository.findByUser(user)
                .stream()
                .map(MapToTicketResponseDTO::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDTO> getAllTickets(String dto) {

        return ticketRepository.findAll(Sort.by("dateOfSubmission").descending())
                .stream()
                .map(MapToTicketResponseDTO::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponseDTO> getTicketsByStatus(String status) {
        return ticketRepository.findByTicketStatus(
                        com.mphasis.csp.enums.TicketStatus.valueOf(status)
                )
                .stream()
                .map(MapToTicketResponseDTO::map)
                .collect(Collectors.toList());
    }
    @Override
    public List<CroDashboardResponseDTO> getCroDashboard() {
        return ticketRepository.getCroDashboard();
    }
}

