package com.lelegspears.project_wev_services.product.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProductCreateDTO {
    @Size(max = 30)
    @NotBlank
    private String name;

    @Size(max = 50)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    private Set<Long> categoryIds = new HashSet<>();

    private String imgURL;

    public ProductCreateDTO(){
    }
}
