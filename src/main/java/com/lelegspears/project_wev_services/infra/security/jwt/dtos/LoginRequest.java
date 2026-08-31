package com.lelegspears.project_wev_services.infra.security.jwt.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        @Schema(example = "Admin")
        String username,

        @NotBlank
        @Schema(example = "123456")
        String password
) {}
