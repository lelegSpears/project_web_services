package com.lelegspears.project_wev_services.order.controller;

import com.lelegspears.project_wev_services.order.entity.Order;
import com.lelegspears.project_wev_services.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id){
        Order order = service.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll(){
        List<Order> orderList = service.findAll();
        return ResponseEntity.ok().body(orderList);
    }

    @PostMapping
    public ResponseEntity<Order> insert(@Valid @RequestBody Order order){
        Order newOrder = service.insert(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newOrder.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newOrder);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> updateById(@PathVariable Long id, @Valid @RequestBody Order newData){
        Order order = service.updateById(id, newData);
        return ResponseEntity.ok().body(order);
    }
}
