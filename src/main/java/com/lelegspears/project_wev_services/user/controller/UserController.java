package com.lelegspears.project_wev_services.user.controller;

import com.lelegspears.project_wev_services.user.service.UserService;
import com.lelegspears.project_wev_services.user.dtos.UserCreateDTO;
import com.lelegspears.project_wev_services.user.dtos.UserResponseDTO;
import com.lelegspears.project_wev_services.user.dtos.UserUpdateDTO;
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

@Tag(name="Controller de Usuários", description = "Operações sobre o Gerenciamento de Usuários")
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @Operation(summary = "Busca Usuário por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        UserResponseDTO user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Busca todos os Usuários")
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable){
        Page<UserResponseDTO> userList = service.findAll(pageable);
        return ResponseEntity.ok(userList);
    }

    @Operation(summary = "Cadastra um Usuário")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO userDTO){
        UserResponseDTO newUser = service.insert(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @Operation(summary = "Deleta um Usuário por ID")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza Usuário por ID")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('USER')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> partialUpdateById(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO newData){
        UserResponseDTO user = service.partialUpdateById(id, newData);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Promove Usuário para ADMIN", description = "Apenas ADMINs podem Promover outros Usuários")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/promote/{id}")
    public ResponseEntity<UserResponseDTO> promoteToAdmin(@PathVariable Long id){
        UserResponseDTO user = service.promoteToAdmin(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Rebaixa Usuário para USER", description = "Apenas ADMINs podem Rebaixar outros Usuários")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/demote/{id}")
    public ResponseEntity<UserResponseDTO> demoteToUser(@PathVariable Long id){
        UserResponseDTO user = service.demoteToUser(id);
        return ResponseEntity.ok(user);
    }
}
