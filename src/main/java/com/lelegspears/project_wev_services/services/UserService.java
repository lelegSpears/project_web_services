package com.lelegspears.project_wev_services.services;

import com.lelegspears.project_wev_services.entities.User;
import com.lelegspears.project_wev_services.exceptions.ResourceNotFoundException;
import com.lelegspears.project_wev_services.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public User insert(User user){
        repository.save(user);
        return user;
    }

    public void deleteById(Long id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }
    }

    @Transactional
    public User updateById(Long id, User newData){
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(user, newData);
        return user;
    }

    private void updateData(User oldData, User newData){
        oldData.setName(newData.getName());
        oldData.setPhone(newData.getPhone());
        oldData.setPassword(newData.getPassword());
        oldData.setEmail(newData.getEmail());
    }
}
