package com.lelegspears.project_wev_services.services;

import com.lelegspears.project_wev_services.entities.Product;
import com.lelegspears.project_wev_services.services.exceptions.DatabaseException;
import com.lelegspears.project_wev_services.services.exceptions.ResourceNotFoundException;
import com.lelegspears.project_wev_services.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Product> findAll(){
        return repository.findAll();
    }

    @Transactional
    public Product insert(Product product){
        repository.save(product);
        return product;
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

    @Transactional
    public Product updateById(Long id, Product newData){
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(product, newData);
        return product;
    }

    private void updateData(Product oldData, Product newData){
        oldData.setDescription(newData.getDescription());
        oldData.setName(newData.getName());
        oldData.setPrice(newData.getPrice());
        oldData.setImgURL(newData.getImgURL());
    }
}
