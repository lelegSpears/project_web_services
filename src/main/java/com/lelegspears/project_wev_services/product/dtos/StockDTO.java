package com.lelegspears.project_wev_services.product.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockDTO (
        @Positive @NotNull @Max(value = 10000, message = "The maximun quantity per operation is 10,000")
        Integer stockQuantity
){}
