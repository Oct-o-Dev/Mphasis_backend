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
}
