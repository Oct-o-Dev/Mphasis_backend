package com.mphasis.csp.enums;

public enum TicketStatus {
    PENDING_CUSTOMER,
    PENDING_CRO,
    PENDING_MANAGER,
    CLOSED_RESOLVED,
    CLOSED_REJECTED;

    public TicketStatus getStatusUpdate(ServiceAction action) {

        // On COMMENT : no state change
        if (action == ServiceAction.COMMENT) {
            return this;
        }

        return switch (this) {

            case PENDING_CUSTOMER -> switch (action) {
                case ESCALATE_TO_CRO -> TicketStatus.PENDING_CRO;
                default -> {
                    printInvalidStatusUpdate(action);
                    yield this;
                }
            };

            case PENDING_CRO -> switch (action) {
                case RETURN_TO_CUSTOMER -> TicketStatus.PENDING_CUSTOMER;
                case ESCALATE_TO_MANAGER -> TicketStatus.PENDING_MANAGER;
                case RESOLVE -> TicketStatus.CLOSED_RESOLVED;
                case REJECT -> TicketStatus.CLOSED_REJECTED;
                default -> {
                    printInvalidStatusUpdate(action);
                    yield this;
                }
            };

            case PENDING_MANAGER -> switch (action) {
                case RETURN_TO_CUSTOMER -> TicketStatus.PENDING_CUSTOMER;
                case RESOLVE -> TicketStatus.CLOSED_RESOLVED;
                case REJECT -> TicketStatus.CLOSED_REJECTED;
                default -> {
                    printInvalidStatusUpdate(action);
                    yield this;
                }
            };

            case CLOSED_REJECTED, CLOSED_RESOLVED -> {
                printInvalidStatusUpdate(action);
                yield this;
            }
        };
    }

    private void printInvalidStatusUpdate(ServiceAction action) {
        System.out.println("Invalid State Update\n      STATUS: " + this.name() + " ACTION: " + action.name());
    }
}
