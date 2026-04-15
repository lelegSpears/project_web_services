package com.lelegspears.project_wev_services.resources.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer  status;
    private String error;
    private String message;
    private String path;

    public StandardError(){
    }

    public StandardError(Integer status, String error, String message, String path){
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
