package com.lelegspears.project_wev_services.product.service;

import com.lelegspears.project_wev_services.category.entity.Category;
import com.lelegspears.project_wev_services.category.repository.CategoryRepository;
import com.lelegspears.project_wev_services.product.dtos.ProductCreateDTO;
import com.lelegspears.project_wev_services.product.dtos.ProductResponseDTO;
import com.lelegspears.project_wev_services.product.dtos.ProductUpdateDTO;
import com.lelegspears.project_wev_services.product.entity.Product;
import com.lelegspears.project_wev_services.product.mapper.ProductMapper;
import com.lelegspears.project_wev_services.product.repository.ProductRepository;
import com.lelegspears.project_wev_services.exception.service.DatabaseException;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return productMapper.toDTO(product);
    }

    public Page<ProductResponseDTO> findAll(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toDTO);
    }

    @Transactional
    public ProductResponseDTO insert(ProductCreateDTO newProduct){
        Product product = productMapper.toEntity(newProduct);
        addCategories(product, newProduct.getCategoryIds());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    private void addCategories(Product product, Set<Long> categoryIds){
        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(categoryId));
            product.addCategory(category);
        }
    }

    @Transactional
    public void deleteById(Long id){
        try {
            productRepository.deleteById(id);
            productRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation: cannot delete entity");
        }
    }

    @Transactional
    public ProductResponseDTO updateById(Long id, ProductUpdateDTO newData){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        productMapper.updateEntity(newData, product);
        updateCategories(product, newData.getCategoryIds());
        return productMapper.toDTO(product);
    }

    private void updateCategories(Product product, Set<Long> categoryIds){
        product.getCategories().clear();

        for(Long id : categoryIds){
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id));

            product.addCategory(category);
        }
    }

}
