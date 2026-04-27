package com.lelegspears.project_wev_services.user.controller;

import com.lelegspears.project_wev_services.user.service.UserService;
import com.lelegspears.project_wev_services.user.dtos.UserCreateDTO;
import com.lelegspears.project_wev_services.user.dtos.UserResponseDTO;
import com.lelegspears.project_wev_services.user.dtos.UserUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        UserResponseDTO user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<UserResponseDTO> userList = service.findAll();
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO userDTO){
        UserResponseDTO newUser = service.insert(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> updateById(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO newData){
        UserResponseDTO user = service.updateById(id, newData);
        return ResponseEntity.ok(user);
    }
}
