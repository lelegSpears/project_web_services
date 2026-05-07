package com.lelegspears.project_wev_services.order.dtos;

import com.lelegspears.project_wev_services.order.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class OrderUpdateDTO {
    Set<OrderItemCreateDTO> items = new HashSet<>();

    public OrderUpdateDTO() {
    }
}
