package com.lelegspears.project_wev_services.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateDTO {
    @Size(max = 55)
    private String name;

    @Email
    private String email;

    @Size(min = 4,max = 40, message = "Password must have at least 4 characters")
    private String password;

    @Pattern(regexp = "\\d{10,11}", message = "Phone must have 10 or 11 digits")  // Limpar e normalizar futuramente
    private String phone;

    public UserUpdateDTO() {
    }
}