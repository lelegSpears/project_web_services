package com.lelegspears.project_wev_services.category.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryCreateDTO {
    @NotBlank
    @Size(max = 55)
    private String name;

    public CategoryCreateDTO(){}
}
