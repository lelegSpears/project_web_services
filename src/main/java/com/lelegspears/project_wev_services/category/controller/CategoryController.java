package com.lelegspears.project_wev_services.category.controller;

import com.lelegspears.project_wev_services.category.dtos.CategoryCreateDTO;
import com.lelegspears.project_wev_services.category.dtos.CategoryResponseDTO;
import com.lelegspears.project_wev_services.category.dtos.CategoryUpdateDTO;
import com.lelegspears.project_wev_services.category.service.CategoryService;
import com.lelegspears.project_wev_services.category.entity.Category;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id){
        CategoryResponseDTO category = service.findById(id);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<CategoryResponseDTO> categoryList = service.findAll(pageable);
        return ResponseEntity.ok().body(categoryList);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> insert(@Valid @RequestBody CategoryCreateDTO category){
        CategoryResponseDTO newCategory = service.insert(category);
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

    @PatchMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO newData){
        CategoryResponseDTO category = service.updateById(id, newData);
        return ResponseEntity.ok().body(category);
    }
}
