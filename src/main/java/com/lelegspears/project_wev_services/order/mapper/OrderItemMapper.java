package com.lelegspears.project_wev_services.order.mapper;

import com.lelegspears.project_wev_services.order.dtos.OrderItemCreateDTO;
import com.lelegspears.project_wev_services.order.dtos.OrderItemResponseDTO;
import com.lelegspears.project_wev_services.order.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "subTotal", target = "subTotal")
    OrderItemResponseDTO toDTO(OrderItem entity);
}