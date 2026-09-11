package com.lelegspears.project_wev_services.order.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class OrderCreateDTO {

    @NotEmpty
    private Set<OrderItemCreateDTO> items = new HashSet<>();

    public OrderCreateDTO() {}
}