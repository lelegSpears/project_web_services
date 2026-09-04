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
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Slf4j
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

        log.info("Product found with Id:{}", id);

        return productMapper.toDTO(product);
    }

    public Page<ProductResponseDTO> findAll(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);

        log.debug("Products Page found with: [ Page:{} size:{} totalElements:{} ]", products.getNumber(), products.getSize(), products.getTotalElements());

        return products.map(productMapper::toDTO);
    }

    @Transactional
    public ProductResponseDTO insert(ProductCreateDTO newProduct){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Product product = productMapper.toEntity(newProduct);
        addCategories(product, newProduct.getCategoryIds());
        productRepository.save(product);

        log.info("Product with: [ Id:{} Name:{} ] Registered By {}", product.getId(), product.getName(), authentication.getName());

        return productMapper.toDTO(product);
    }

    private void addCategories(Product product, Set<Long> categoryIds){
        for (Long categoryId : categoryIds) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(categoryId));
            product.addCategory(category);
        }
        log.debug(
                "Categories added to Product: [ Id: {}, Quantity: {} ]",
                product.getId(),
                categoryIds.size()
        );
    }

    @Transactional
    public void deleteById(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            productRepository.deleteById(id);
            productRepository.flush();
            log.info("Product with Id:{} Deleted By {}", id, authentication.getName());
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation: cannot delete entity");
        }
    }

    @Transactional
    public ProductResponseDTO updateById(Long id, ProductUpdateDTO newData){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        productMapper.updateEntity(newData, product);
        updateCategories(product, newData.getCategoryIds());
        log.info("Product with Id:{} Updated By {}", id, authentication.getName());
        return productMapper.toDTO(product);
    }

    private void updateCategories(Product product, Set<Long> categoryIds){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        product.getCategories().clear();

        for(Long id : categoryIds){
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id));

            product.addCategory(category);
        }
        log.debug(
                "Product categories updated: [ ProductId: {}, Quantity: {} ]",
                product.getId(),
                categoryIds.size()
        );
    }

}
