package com.lelegspears.project_wev_services.services;


import com.lelegspears.project_wev_services.entities.Order;
import com.lelegspears.project_wev_services.services.exceptions.DatabaseException;
import com.lelegspears.project_wev_services.services.exceptions.ResourceNotFoundException;
import com.lelegspears.project_wev_services.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public Order findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Order> findAll(){
        return repository.findAll();
    }

    public Order insert(Order order){
        repository.save(order);
        return order;
    }

    public void deleteById(Long id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public Order updateById(Long id, Order newData){
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(order, newData);
        return order;
    }

    private void updateData(Order oldData, Order newData){
        oldData.setClient(newData.getClient());
        oldData.setMoment(newData.getMoment());
    }
}
