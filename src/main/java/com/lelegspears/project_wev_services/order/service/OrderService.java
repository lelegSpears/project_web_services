package com.lelegspears.project_wev_services.order.service;


import com.lelegspears.project_wev_services.order.entity.Order;
import com.lelegspears.project_wev_services.order.entity.OrderItem;
import com.lelegspears.project_wev_services.order.repository.OrderRepository;
import com.lelegspears.project_wev_services.exception.service.DatabaseException;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
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

    @Transactional
    public Order insert(Order order){
        repository.save(order);
        return order;
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
    public Order updateById(Long id, Order newData){
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(order, newData);
        return order;
    }

    private void updateData(Order oldData, Order newData){
        oldData.setClient(newData.getClient());
        oldData.setMoment(newData.getMoment());
        oldData.setOrderStatus(newData.getOrderStatus());

        oldData.getItems().clear();
        for (OrderItem item : newData.getItems()) {
            item.setOrder(oldData);
            oldData.getItems().add(item);
        }

        if (newData.getPayment() != null) {
            newData.getPayment().setOrder(oldData);
        }
        oldData.setPayment(newData.getPayment());
    }
}
