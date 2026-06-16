package com.mphasis.csp.service;

import com.mphasis.csp.dto.request.*;
import com.mphasis.csp.dto.response.CroDashboardResponseDTO;
import com.mphasis.csp.dto.response.TicketHistoryResponseDTO;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @Autowired
    private ITicketServiceService ticketServiceService;

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
                .assignedTo(ticketServiceService.findLeastLoadedUserByRole("CRO"))
                .build();

        Ticket saved = ticketRepository.save(ticket);
        return MapToTicketResponseDTO.map(saved);
    }

    @Override
    public TicketResponseDTO getTicket(Integer ticketId, String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketRepository
                .findByTicketIdAndUser(ticketId, user)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return MapToTicketResponseDTO.map(ticket);
    }

    @Override
    public TicketHistoryResponseDTO getTicketHistory(Integer ticketId, Authentication authentication) {

        String email = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isCustomer = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));

        boolean isCRO = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CRO"));

        Optional<Ticket> ticketOpt;

        if (isAdmin) {
            ticketOpt = ticketRepository.findById(ticketId);
        } else if (isCustomer) {
            ticketOpt = ticketRepository.findByIdAndUserEmailId(ticketId, email);
        } else if (isCRO) {
            ticketOpt = ticketRepository.findByIdAndAssignedToEmailId(ticketId, email);
        } else {
            throw new AccessDeniedException("Unauthorized role");
        }

        Ticket ticket = ticketOpt
                .orElseThrow(() -> new AccessDeniedException("Access denied or ticket not found"));
        List<com.mphasis.csp.model.TicketService> history =
                ticketServiceRepository.findTicketServiceHistory(ticketId);

        return TicketHistoryResponseDTO.mapToDTO(ticket, history);
    }

    @Override
    public List<TicketResponseDTO> getTicketsRaisedBy(String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketRepository.findByUser(user)
                .stream()
                .map(MapToTicketResponseDTO::map)
                .toList();
    }

    @Override
    public List<TicketResponseDTO> getTicketsAssignedTo(String email) {
        return ticketRepository.findTicketsByAssignedUserEmail(email)
                .stream()
                .map(MapToTicketResponseDTO::map)
                .toList();
    }

    @Override
    public List<TicketResponseDTO> getAllTickets() {

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

