package com.lelegspears.project_wev_services.category.controller;

import com.lelegspears.project_wev_services.category.dtos.CategoryCreateDTO;
import com.lelegspears.project_wev_services.category.dtos.CategoryResponseDTO;
import com.lelegspears.project_wev_services.category.dtos.CategoryUpdateDTO;
import com.lelegspears.project_wev_services.category.service.CategoryService;
import com.lelegspears.project_wev_services.category.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name="Controller de Categorías", description = "Operações sobre o Gerenciamento de Categorías")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @Operation(summary = "Busca Categoria por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id){
        CategoryResponseDTO category = service.findById(id);
        return ResponseEntity.ok().body(category);
    }

    @Operation(summary = "Busca todas as Categoria")
    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable){
        Page<CategoryResponseDTO> categoryList = service.findAll(pageable);
        return ResponseEntity.ok().body(categoryList);
    }

    @Operation(summary = "Cria Categoria", description = "Apenas ADMINs podem criar Categorías.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> insert(@Valid @RequestBody CategoryCreateDTO category){
        CategoryResponseDTO newCategory = service.insert(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newCategory.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newCategory);
    }

    @Operation(summary = "Deleta Categoria por ID", description = "Apenas ADMINs podem deletar Categorías.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza Categoria por ID", description = "Apenas ADMINs podem atualizar Categorías.")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO newData){
        CategoryResponseDTO category = service.updateById(id, newData);
        return ResponseEntity.ok().body(category);
    }
}
