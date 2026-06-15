package com.mphasis.csp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTicketStatusUpdateException.class)
    public ResponseEntity<?> handleInvalidStatusUpdate(InvalidTicketStatusUpdateException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("This action type is not allowed on this ticket status.");
    }

    @ExceptionHandler(DebitCardNotFoundException.class)
    public ResponseEntity<?> handleDebitCardNotFound(DebitCardNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("This debit card does not belong to logged in user");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("User not found");
    }
}
