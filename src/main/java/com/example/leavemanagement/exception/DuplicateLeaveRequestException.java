package com.example.leavemanagement.exception;

public class DuplicateLeaveRequestException extends RuntimeException {

    public DuplicateLeaveRequestException(String message) {
        super(message);
    }
}
