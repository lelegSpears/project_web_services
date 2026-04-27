package com.lelegspears.project_wev_services.category.controller;

import com.lelegspears.project_wev_services.category.service.CategoryService;
import com.lelegspears.project_wev_services.category.entity.Category;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        Category category = service.findById(id);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> categoryList = service.findAll();
        return ResponseEntity.ok().body(categoryList);
    }

    @PostMapping
    public ResponseEntity<Category> insert(@Valid @RequestBody Category category){
        Category newCategory = service.insert(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newCategory.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newCategory);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Category> updateById(@PathVariable Long id, @Valid @RequestBody Category newData){
        Category category = service.updateById(id, newData);
        return ResponseEntity.ok().body(category);
    }
}
