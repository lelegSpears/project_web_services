package com.lelegspears.project_wev_services.order.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class PaymentResponseDTO {
    private Long id;
    private Instant moment;

    public PaymentResponseDTO(){}
}
