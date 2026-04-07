package com.lelegspears.project_wev_services.resources;

import com.lelegspears.project_wev_services.entities.User;
import com.lelegspears.project_wev_services.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService service;

    public UserResource(UserService service){
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = service.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> userList = service.findAll();
        return ResponseEntity.ok().body(userList);
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user){
        User newUser = service.insert(user);
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateById(@PathVariable Long id, @RequestBody User newData){
        User user = service.updateById(id, newData);
        return ResponseEntity.ok().body(user);
    }
}
