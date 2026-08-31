package com.lelegspears.project_wev_services.order.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class OrderCreateDTO {
    @NotNull
    @Schema(example = "1")
    private Long clientId;

    @NotEmpty
    private Set<OrderItemCreateDTO> items = new HashSet<>();

    public OrderCreateDTO() {}
}