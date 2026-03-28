package com.ogidazepam.expense_tracker.util;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class ExpenseException {
    private HttpStatus httpStatus;
    private String message;
    private Instant timestamp;

    public ExpenseException() {
    }

    public ExpenseException(HttpStatus httpStatus, String message, Instant timestamp) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
