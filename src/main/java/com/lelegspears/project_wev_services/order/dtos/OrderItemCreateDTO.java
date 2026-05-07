package com.lelegspears.project_wev_services.order.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemCreateDTO {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer quantity;

    public OrderItemCreateDTO(){}
}
