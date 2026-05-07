package com.lelegspears.project_wev_services.order.mapper;

import com.lelegspears.project_wev_services.order.dtos.OrderCreateDTO;
import com.lelegspears.project_wev_services.order.dtos.OrderResponseDTO;
import com.lelegspears.project_wev_services.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "total", target = "total")
    OrderResponseDTO toDTO(Order order);

    @Mapping(target = "moment", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toEntity(OrderCreateDTO dto);
}