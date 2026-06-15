package com.mphasis.csp.exception;

import com.mphasis.csp.model.User;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(User user) {
        super(String.format("User %s not found",
                user.toString()));
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
