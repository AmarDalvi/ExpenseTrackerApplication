package com.assignment.expense_tracker_application.exception;

import org.springframework.http.HttpStatus;



import org.springframework.http.HttpStatus;

public class ExpenseTrackerAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public ExpenseTrackerAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ExpenseTrackerAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}