package com.ogidazepam.expense_tracker.util;

import com.ogidazepam.expense_tracker.util.exceptions.EntityNotCreatedException;
import com.ogidazepam.expense_tracker.util.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;
import java.time.Instant;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EntityException> handleValidationExceptions(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StringBuilder errors = new StringBuilder("Validation failed: ");
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.append("[").append(fieldName).append(": ").append(errorMessage).append("] ");
        });

        EntityException entityException = formException(status, errors.toString());
        return new ResponseEntity<>(entityException, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<EntityException> handleJsonErrors(HttpMessageNotReadableException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = "Invalid input format";

        if(e.getCause() instanceof InvalidFormatException){
            message = "Invalid value for category";
        }

        EntityException entityException = formException(status, message);
        return new ResponseEntity<>(entityException, status);
    }

    @ExceptionHandler
    public ResponseEntity<EntityException> entityNotFoundException(EntityNotFoundException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        EntityException entityException = formException(status, e.getMessage());
        return new ResponseEntity<>(entityException, status);
    }

    @ExceptionHandler
    public ResponseEntity<EntityException> entityNotCreatedException(EntityNotCreatedException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        EntityException entityException = formException(status, e.getMessage());
        return new ResponseEntity<>(entityException, status);
    }

    private EntityException formException(HttpStatus status, String errorMessage){
        return new EntityException(
                status,
                errorMessage,
                Instant.now()
        );
    }
}
