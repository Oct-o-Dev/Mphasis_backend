package com.mphasis.csp.enums;

public enum ServiceAction {

    // Does not update status
    COMMENT,

    // Step down
    RETURN_TO_CUSTOMER,

    // Step up
    ESCALATE_TO_CRO,
    ESCALATE_TO_MANAGER,

    // Close
    RESOLVE,
    REJECT
}
