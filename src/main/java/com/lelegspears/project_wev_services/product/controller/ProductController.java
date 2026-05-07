package com.lelegspears.project_wev_services.product.controller;

import com.lelegspears.project_wev_services.product.dtos.ProductCreateDTO;
import com.lelegspears.project_wev_services.product.dtos.ProductResponseDTO;
import com.lelegspears.project_wev_services.product.dtos.ProductUpdateDTO;
import com.lelegspears.project_wev_services.product.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        ProductResponseDTO product = service.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ProductResponseDTO> productList = service.findAll(pageable);
        return ResponseEntity.ok().body(productList);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@Valid @RequestBody ProductCreateDTO product){
        ProductResponseDTO newProduct = service.insert(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newProduct.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO newData){
        ProductResponseDTO product = service.updateById(id, newData);
        return ResponseEntity.ok().body(product);
    }
}
