package com.mphasis.csp.exception;

import com.mphasis.csp.model.DebitCard;
import com.mphasis.csp.model.User;

public class DebitCardNotFoundException extends RuntimeException {

    public DebitCardNotFoundException(User user, DebitCard debitCard) {
        super(String.format("User: %s (%s %s) Tried to access DC: %s, does not belong to user",
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                debitCard.getDebitCardNumber()));
    }

    public DebitCardNotFoundException(User user, String debitCardLast4Digits) {
        super(String.format("User: %s (%s %s) Tried to access DC (last 4 digits): %s, does not belong to user",
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                debitCardLast4Digits));
    }

    public DebitCardNotFoundException(String message) {
        super(message);
    }
}
