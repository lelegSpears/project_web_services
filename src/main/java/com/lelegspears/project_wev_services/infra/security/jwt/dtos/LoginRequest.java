package com.lelegspears.project_wev_services.infra.security.jwt.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String username,

        @NotBlank
        String password
) {}
