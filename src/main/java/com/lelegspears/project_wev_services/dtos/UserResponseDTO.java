package com.lelegspears.project_wev_services.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;

    public UserResponseDTO() {
    }
}