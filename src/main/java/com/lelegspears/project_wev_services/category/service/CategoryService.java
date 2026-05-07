package com.lelegspears.project_wev_services.category.service;

import com.lelegspears.project_wev_services.category.dtos.CategoryCreateDTO;
import com.lelegspears.project_wev_services.category.dtos.CategoryResponseDTO;
import com.lelegspears.project_wev_services.category.dtos.CategoryUpdateDTO;
import com.lelegspears.project_wev_services.category.entity.Category;
import com.lelegspears.project_wev_services.category.mapper.CategoryMapper;
import com.lelegspears.project_wev_services.category.repository.CategoryRepository;
import com.lelegspears.project_wev_services.exception.service.DatabaseException;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponseDTO findById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return categoryMapper.toDTO(category);
    }

    public Page<CategoryResponseDTO> findAll(Pageable pageable){
        Page<Category> categoryList = categoryRepository.findAll(pageable);
        return categoryList.map(categoryMapper::toDTO);
    }

    @Transactional
    public CategoryResponseDTO insert(CategoryCreateDTO dto){
        Category category = categoryRepository.save(categoryMapper.toEntity(dto));
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public void deleteById(Long id){
        try {
            categoryRepository.deleteById(id);
            categoryRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation: cannot delete entity");
        }
    }

    @Transactional // Dirty Checking, JPA salva automaticamente o objeto
    public CategoryResponseDTO updateById(Long id, CategoryUpdateDTO newData){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        categoryMapper.updateEntity(newData, category);
        return categoryMapper.toDTO(category);
    }
}
