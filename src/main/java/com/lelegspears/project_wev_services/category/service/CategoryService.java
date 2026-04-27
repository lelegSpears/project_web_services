package com.lelegspears.project_wev_services.category.service;

import com.lelegspears.project_wev_services.category.entity.Category;
import com.lelegspears.project_wev_services.category.repository.CategoryRepository;
import com.lelegspears.project_wev_services.exception.service.DatabaseException;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public Category findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Category> findAll(){
        return repository.findAll();
    }

    @Transactional
    public Category insert(Category category){
        repository.save(category);
        return category;
    }

    @Transactional
    public void deleteById(Long id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation: cannot delete entity");
        }
    }

    @Transactional // Dirty Checking, JPA salva automaticamente o objeto
    public Category updateById(Long id, Category newData){
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(category, newData);
        return category;
    }

    private void updateData(Category oldData, Category newData){
        oldData.setName(newData.getName());
    }
}
