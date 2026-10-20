package com.lelegspears.project_wev_services.exception.handler;

import com.lelegspears.project_wev_services.exception.service.DatabaseException;
import com.lelegspears.project_wev_services.exception.service.InvalidOrderStateException;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import com.lelegspears.project_wev_services.exception.service.StockMaxExceededException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource Not Found";
        HttpStatus status = HttpStatus.NOT_FOUND;

        log.warn("Resource not found: [ path: {}, message: {} ]", request.getRequestURI(), e.getMessage());

        StandardError err = new StandardError(status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        log.error("Database error: [ path: {}, message: {} ]", request.getRequestURI(), e.getMessage(), e);

        StandardError err = new StandardError(status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidOrderStateException.class)
    public ResponseEntity<StandardError> invalidOrderState(InvalidOrderStateException e, HttpServletRequest request) {

        String error = "Invalid Order State";
        HttpStatus status = HttpStatus.CONFLICT;

        log.warn("Invalid order state: [ path: {}, message: {} ]", request.getRequestURI(), e.getMessage());

        StandardError err = new StandardError(status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

        String error = "Validation Exception";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        log.warn("Validation failed: [ path: {} ]", request.getRequestURI());

        ValidationError err = new ValidationError(
                status.value(),
                error,
                "Validation failed",
                request.getRequestURI()
        );

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            log.debug("Validation error: [ field: {}, message: {} ]", f.getField(), f.getDefaultMessage());
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<StandardError> handlePropertyReferenceException( // Para Pageable
            PropertyReferenceException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;


        log.warn("Invalid sorting parameter: [ path: {}, message: {} ]", request.getRequestURI(), e.getMessage());

        StandardError error = new StandardError(
                status.value(),
                "Invalid sorting parameter",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        log.warn("Invalid argument: [ path: {}, message: {} ]", request.getRequestURI(), e.getMessage());

        StandardError err = new StandardError(
                status.value(),
                "Invalid argument",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(StockMaxExceededException.class)
    public ResponseEntity<StandardError> stockMaxExceededException(StockMaxExceededException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        log.warn("Stock Max Exceeded: [ path: {}, message: {} ]", request.getRequestURI(), e.getMessage());

        StandardError err = new StandardError(
                status.value(),
                "Stock Max Exceeded",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }
}
