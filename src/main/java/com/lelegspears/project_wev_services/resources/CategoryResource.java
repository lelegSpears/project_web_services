package com.lelegspears.project_wev_services.resources;

import com.lelegspears.project_wev_services.entities.Category;
import com.lelegspears.project_wev_services.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    private final CategoryService service;

    public CategoryResource(CategoryService service){
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
