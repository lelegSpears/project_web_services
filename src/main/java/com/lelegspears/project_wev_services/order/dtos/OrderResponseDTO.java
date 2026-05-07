package com.lelegspears.project_wev_services.order.dtos;

import com.lelegspears.project_wev_services.order.enums.OrderStatus;
import com.lelegspears.project_wev_services.user.dtos.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class OrderResponseDTO {
    private Long id;
    private Instant moment;
    private UserResponseDTO client;
    private OrderStatus orderStatus;
    private Set<OrderItemResponseDTO> items = new HashSet<>();
    private PaymentResponseDTO payment;
    private BigDecimal total;

    public OrderResponseDTO(){}
}
