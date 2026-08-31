package com.lelegspears.project_wev_services.order.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemCreateDTO {

    @NotNull
    @Schema(example = "1")
    private Long productId;

    @NotNull
    @Min(1)
    @Max(100)
    @Schema(example = "2")
    private Integer quantity;

    public OrderItemCreateDTO(){}
}
