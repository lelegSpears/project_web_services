package com.lelegspears.project_wev_services.exception.service;

public class InvalidOrderStateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidOrderStateException(String message) {
        super(message);
    }
}
