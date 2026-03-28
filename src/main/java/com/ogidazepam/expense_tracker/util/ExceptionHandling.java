package com.ogidazepam.expense_tracker.util;

import com.ogidazepam.expense_tracker.util.exceptions.ExpenseNotCreatedException;
import com.ogidazepam.expense_tracker.util.exceptions.ExpenseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler
    public ResponseEntity<ExpenseException> expenseNotFoundException(ExpenseNotFoundException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExpenseException expenseException = formExpenseException(status, e.getMessage());
        return new ResponseEntity<>(expenseException, status);
    }

    @ExceptionHandler
    public ResponseEntity<ExpenseException> expenseNotCreatedException(ExpenseNotCreatedException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExpenseException expenseException = formExpenseException(status, e.getMessage());
        return new ResponseEntity<>(expenseException, status);
    }

    private ExpenseException formExpenseException(HttpStatus status, String errorMessage){
        return new ExpenseException(
                status,
                errorMessage,
                Instant.now()
        );
    }
}
