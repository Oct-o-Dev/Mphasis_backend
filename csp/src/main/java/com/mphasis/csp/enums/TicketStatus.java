package com.mphasis.csp.enums;

import com.mphasis.csp.exception.InvalidTicketStatusUpdateException;

public enum TicketStatus {
    PENDING_CUSTOMER,
    PENDING_CRO,
    PENDING_MANAGER,
    CLOSED_RESOLVED,
    CLOSED_REJECTED;

    public TicketStatus getStatusUpdate(ServiceAction action) throws InvalidTicketStatusUpdateException {

        // On COMMENT : no state change
        if (action == ServiceAction.COMMENT) {
            return this;
        }

        return switch (this) {

            case PENDING_CUSTOMER -> switch (action) {
                case ESCALATE_TO_CRO -> TicketStatus.PENDING_CRO;
                default -> throw new InvalidTicketStatusUpdateException(this, action);
            };

            case PENDING_CRO -> switch (action) {
                case RETURN_TO_CUSTOMER -> TicketStatus.PENDING_CUSTOMER;
                case ESCALATE_TO_MANAGER -> TicketStatus.PENDING_MANAGER;
                case RESOLVE -> TicketStatus.CLOSED_RESOLVED;
                case REJECT -> TicketStatus.CLOSED_REJECTED;
                default -> throw new InvalidTicketStatusUpdateException(this, action);
            };

            case PENDING_MANAGER -> switch (action) {
                case RETURN_TO_CUSTOMER -> TicketStatus.PENDING_CUSTOMER;
                case RESOLVE -> TicketStatus.CLOSED_RESOLVED;
                case REJECT -> TicketStatus.CLOSED_REJECTED;
                default -> throw new InvalidTicketStatusUpdateException(this, action);
            };

            case CLOSED_REJECTED, CLOSED_RESOLVED -> throw new InvalidTicketStatusUpdateException(this, action);
        };
    }
}
