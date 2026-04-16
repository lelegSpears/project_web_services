package com.lelegspears.project_wev_services.resources;

import com.lelegspears.project_wev_services.entities.Product;
import com.lelegspears.project_wev_services.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductResource {
    private final ProductService service;

    public ProductResource(ProductService service){
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Product product = service.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        List<Product> productList = service.findAll();
        return ResponseEntity.ok().body(productList);
    }

    @PostMapping
    public ResponseEntity<Product> insert(@Valid @RequestBody Product product){
        Product newproduct = service.insert(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newproduct.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newproduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateById(@PathVariable Long id, @Valid @RequestBody Product newData){
        Product product = service.updateById(id, newData);
        return ResponseEntity.ok().body(product);
    }
}
