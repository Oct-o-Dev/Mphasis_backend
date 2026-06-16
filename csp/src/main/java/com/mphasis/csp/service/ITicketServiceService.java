package com.mphasis.csp.service;

import com.mphasis.csp.dto.request.RaiseServiceRequestDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;
import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.exception.InvalidTicketStatusUpdateException;
import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.TicketService;
import com.mphasis.csp.model.User;

public interface ITicketServiceService {

    TicketResponseDTO raiseService(RaiseServiceRequestDTO dto, String email) throws InvalidTicketStatusUpdateException;
    TicketService applySystemAction(Ticket ticket, ServiceAction action);
    User getUserByRole(String role);
    public User findLeastLoadedUserByRole(String role);
}
