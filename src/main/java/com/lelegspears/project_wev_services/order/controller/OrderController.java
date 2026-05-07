package com.lelegspears.project_wev_services.order.controller;

import com.lelegspears.project_wev_services.order.dtos.OrderCreateDTO;
import com.lelegspears.project_wev_services.order.dtos.OrderResponseDTO;
import com.lelegspears.project_wev_services.order.dtos.OrderUpdateDTO;
import com.lelegspears.project_wev_services.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id){
        OrderResponseDTO order = service.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<OrderResponseDTO> orderList = service.findAll(pageable);
        return ResponseEntity.ok().body(orderList);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> insert(@Valid @RequestBody OrderCreateDTO dto){
        OrderResponseDTO newOrder = service.insert(dto);
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

    @PatchMapping(value = "/{id}")
    public ResponseEntity<OrderResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody OrderUpdateDTO dto){
        OrderResponseDTO order = service.updateById(id, dto);
        return ResponseEntity.ok().body(order);
    }
}
