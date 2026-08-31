package com.lelegspears.project_wev_services.product.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "Notebook")
    private String name;

    @Schema(example = "Aparelho eletrônico móvel.")
    @Size(max = 50)
    private String description;

    @NotNull
    @Positive
    @Schema(example = "3000.00")
    private BigDecimal price;

    @Schema(description = "IDs das categorias associadas ao produto.", example = "[1, 2]")
    private Set<Long> categoryIds = new HashSet<>();

    @Schema(example = "https://exemplo.com/notebook.jpg")
    private String imgURL;

    public ProductCreateDTO(){
    }
}
