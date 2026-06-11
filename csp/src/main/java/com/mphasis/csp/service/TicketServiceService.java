package com.mphasis.csp.service;

import com.mphasis.csp.dto.request.RaiseServiceRequestDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;
import com.mphasis.csp.enums.TicketStatus;
import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.TicketRepository;
import com.mphasis.csp.repository.TicketServiceRepository;
import com.mphasis.csp.repository.UserRepository;
import com.mphasis.csp.util.MapToTicketResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketServiceService implements ITicketServiceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketServiceRepository ticketServiceRepository;

    @Override
    public TicketResponseDTO raiseService(RaiseServiceRequestDTO dto, String email) {

        //  Step 1: Ticket fetch
        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        //  Step 2: Old status
        TicketStatus oldStatus = ticket.getTicketStatus();

        //  Step 3: Create new service record
        com.mphasis.csp.model.TicketService service =
                new com.mphasis.csp.model.TicketService();

        service.setTicket(ticket);
        service.setServiceAction(dto.getServiceType());
        service.setComment(dto.getComment());
        service.setOldStatus(oldStatus);
        service.setNewStatus(dto.getNewStatus());
        service.setDateOfService(LocalDateTime.now());

        //  FETCH USER FROM DB
        User cro = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        service.setCro(cro);  //  auto-set cro_id

        // Step 5: SAVE into services table
        ticketServiceRepository.save(service);

        //  Step 6: Update ticket status
        ticket.setTicketStatus(dto.getNewStatus());

        Ticket updated = ticketRepository.save(ticket);

        return MapToTicketResponseDTO.map(updated);
    }
}
