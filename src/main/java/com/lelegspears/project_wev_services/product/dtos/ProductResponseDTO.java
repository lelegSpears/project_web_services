package com.lelegspears.project_wev_services.product.dtos;

import com.lelegspears.project_wev_services.category.dtos.CategoryResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProductResponseDTO {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String imgURL;

    private Set<CategoryResponseDTO> categories;

    public ProductResponseDTO(){
    }
}
