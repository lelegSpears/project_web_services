package com.lelegspears.project_wev_services.order.mapper;

import com.lelegspears.project_wev_services.order.dtos.PaymentResponseDTO;
import com.lelegspears.project_wev_services.order.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface    PaymentMapper {
    PaymentResponseDTO toDTO(Payment payment);
}
