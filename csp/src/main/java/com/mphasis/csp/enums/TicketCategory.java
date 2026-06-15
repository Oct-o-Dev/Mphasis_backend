package com.mphasis.csp.enums;

public enum TicketCategory {
    DEBITCARDSERVICE,
    INTERNETBANKING,
    ACCOUNTSERVICE,
    COMPLAINT;

    public int getSLAMinutes() {
        return 1; // same SLA for all
    }

    }
