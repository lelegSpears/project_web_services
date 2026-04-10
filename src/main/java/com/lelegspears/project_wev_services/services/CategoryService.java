package com.lelegspears.project_wev_services.services;

import com.lelegspears.project_wev_services.entities.Category;
import com.lelegspears.project_wev_services.exceptions.ResourceNotFoundException;
import com.lelegspears.project_wev_services.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Category insert(Category Category){
        repository.save(Category);
        return Category;
    }

    public void deleteById(Long id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }
    }

    @Transactional
    public Category updateById(Long id, Category newData){
        Category Category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(Category, newData);
        return Category;
    }

    private void updateData(Category oldData, Category newData){
        oldData.setName(newData.getName());
    }
}
