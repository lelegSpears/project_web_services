package com.lelegspears.project_wev_services.product.controller;

import com.lelegspears.project_wev_services.product.dtos.ProductCreateDTO;
import com.lelegspears.project_wev_services.product.dtos.ProductResponseDTO;
import com.lelegspears.project_wev_services.product.dtos.ProductUpdateDTO;
import com.lelegspears.project_wev_services.product.service.ProductService;
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

@Tag(name = "Controller de Produtos", description = "Operações de Gerenciamento de Produtos.")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @Operation(summary = "Busca Produto por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        ProductResponseDTO product = service.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @Operation(summary = "Busca todos os Produtos")
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable){
        Page<ProductResponseDTO> productList = service.findAll(pageable);
        return ResponseEntity.ok().body(productList);
    }

    @Operation(summary = "Cadastra um Produto", description = "Apenas ADMINs podem Cadastrar Produtos")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@Valid @RequestBody ProductCreateDTO product){
        ProductResponseDTO newProduct = service.insert(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newProduct.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newProduct);
    }

    @Operation(summary = "Exclúi um Produto", description = "Apenas ADMINs podem Apagar Produtos")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza um Produto", description = "Apenas ADMINs podem Atualizar Produtos")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO newData){
        ProductResponseDTO product = service.updateById(id, newData);
        return ResponseEntity.ok().body(product);
    }
}
