package com.mphasis.csp.service;

import com.mphasis.csp.dto.request.RaiseServiceRequestDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;
import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.enums.TicketStatus;
import com.mphasis.csp.exception.InvalidTicketStatusUpdateException;
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
import java.util.List;

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
    public TicketResponseDTO raiseService(RaiseServiceRequestDTO dto, String email) throws InvalidTicketStatusUpdateException {

        //  Fetch old status from linked ticket
        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        TicketStatus oldStatus = ticket.getTicketStatus();
        TicketStatus newStatus = oldStatus.getStatusUpdate(dto.getServiceAction());

        //  Create new service record
        com.mphasis.csp.model.TicketService service =
                new com.mphasis.csp.model.TicketService();

        service.setTicket(ticket);
        service.setServiceAction(dto.getServiceAction());
        service.setComment(dto.getComment());
        service.setOldStatus(oldStatus);
        service.setNewStatus(newStatus);
        service.setDateOfService(LocalDateTime.now());

        //  Fetch linked user that raised this service (Customer, CRO or Manager)
        User cro = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        service.setCro(cro);  //  auto-set cro_id

        // Log service
        ticketServiceRepository.save(service);

        //  Update ticket status on DB
        ticket.setTicketStatus(newStatus);
        Ticket updated = ticketRepository.save(ticket);

        return MapToTicketResponseDTO.map(updated);
    }

    // SYSTEM_LEVEL ACTION(USED BY SCHEDULER)
    public void applySystemAction(Ticket ticket, ServiceAction action) {

        // get current status
        TicketStatus oldStatus = ticket.getTicketStatus();

        // use existing state machine logic (VERY IMPORTANT)
        TicketStatus newStatus = oldStatus.getStatusUpdate(action);

        //Assignment logic added
        if(action == ServiceAction.ESCALATE_TO_CRO){
            User croUser= findLeastLoadedUserByRole("CRO");
            ticket.setAssignedTo(croUser);
        }
        else if(action==ServiceAction.ESCALATE_TO_MANAGER){
            User managerUser=findLeastLoadedUserByRole("ADMIN");
            ticket.setAssignedTo(managerUser);
        }

        // create service log entry (same as raiseService)
        com.mphasis.csp.model.TicketService service =
                new com.mphasis.csp.model.TicketService();

        service.setTicket(ticket);
        service.setServiceAction(action);
        service.setComment("Auto escalation by SLA");
        service.setOldStatus(oldStatus);
        service.setNewStatus(newStatus);
        service.setDateOfService(LocalDateTime.now());

        // system user (optional fallback if needed)
        User systemUser = ticket.getUser();

        service.setCro(systemUser);

        // save service record
        ticketServiceRepository.save(service);

        // update ticket status
        ticket.setTicketStatus(newStatus);

        ticketRepository.save(ticket);
    }

    // HELPER METHOD → FIND USER BY ROLE
    private User getUserByRole(String role) {

        return userRepository.findAll()
                .stream()
                .filter(user -> role.equalsIgnoreCase(user.getRole()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("User with role " + role + " not found"));
    }

    private User findLeastLoadedUserByRole(String role) {

        List<User> users = userRepository.findByRole(role);

        if (users.isEmpty()) {
            throw new RuntimeException("No users found for role: " + role);
        }

        User selectedUser = users.get(0);
        long minTickets = ticketRepository.countByAssignedTo(selectedUser);

        for (User user : users) {

            long ticketCount = ticketRepository.countByAssignedTo(user);

            if (ticketCount < minTickets) {
                minTickets = ticketCount;
                selectedUser = user;
            }
        }

        return selectedUser;
    }

}
