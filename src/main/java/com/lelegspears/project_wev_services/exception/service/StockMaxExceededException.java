package com.lelegspears.project_wev_services.exception.service;

public class StockMaxExceededException extends RuntimeException {
    public StockMaxExceededException(String message) {
        super(message);
    }
}
