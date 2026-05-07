package com.lelegspears.project_wev_services.category.mapper;

import com.lelegspears.project_wev_services.category.dtos.CategoryCreateDTO;
import com.lelegspears.project_wev_services.category.dtos.CategoryResponseDTO;
import com.lelegspears.project_wev_services.category.dtos.CategoryUpdateDTO;
import com.lelegspears.project_wev_services.category.entity.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toDTO(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryCreateDTO dto);

    List<CategoryResponseDTO> toDTOList(List<Category> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateEntity(CategoryUpdateDTO categoryDTO, @MappingTarget Category category);
}
