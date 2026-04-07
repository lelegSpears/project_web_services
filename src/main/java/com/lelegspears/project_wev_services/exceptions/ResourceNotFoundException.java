package com.lelegspears.project_wev_services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Long message){
        super(message);
    }


}
