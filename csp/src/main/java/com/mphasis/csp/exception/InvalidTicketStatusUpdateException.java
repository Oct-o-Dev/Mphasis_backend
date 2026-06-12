package com.mphasis.csp.exception;

import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.enums.TicketStatus;

public class InvalidTicketStatusUpdateException extends RuntimeException {

    public InvalidTicketStatusUpdateException(TicketStatus status, ServiceAction action) {
        super("Invalid status transition: STATUS = " + status + ", ACTION = " + action);
    }

    public InvalidTicketStatusUpdateException(String message) {
        super(message);
    }
}