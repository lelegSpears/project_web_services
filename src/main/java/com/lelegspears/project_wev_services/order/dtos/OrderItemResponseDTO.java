package com.lelegspears.project_wev_services.order.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderItemResponseDTO {
    private Long productId;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;

    public OrderItemResponseDTO(){}
}
