package com.lelegspears.project_wev_services.product.mapper;

import com.lelegspears.project_wev_services.category.mapper.CategoryMapper;
import com.lelegspears.project_wev_services.product.dtos.ProductCreateDTO;
import com.lelegspears.project_wev_services.product.dtos.ProductResponseDTO;
import com.lelegspears.project_wev_services.product.dtos.ProductUpdateDTO;
import com.lelegspears.project_wev_services.product.entity.Product;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses =  {CategoryMapper.class}   )
public interface ProductMapper {

    ProductResponseDTO toDTO(Product product);

    List<ProductResponseDTO> toDTOs(List<Product> products);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Product toEntity(ProductCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateEntity(ProductUpdateDTO dto, @MappingTarget Product product);
}