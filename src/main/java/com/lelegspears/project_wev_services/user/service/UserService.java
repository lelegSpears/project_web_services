package com.lelegspears.project_wev_services.user.service;

import com.lelegspears.project_wev_services.user.entity.User;
import com.lelegspears.project_wev_services.user.dtos.UserCreateDTO;
import com.lelegspears.project_wev_services.user.dtos.UserResponseDTO;
import com.lelegspears.project_wev_services.user.dtos.UserUpdateDTO;
import com.lelegspears.project_wev_services.user.mappers.UserMapper;
import com.lelegspears.project_wev_services.exception.service.DatabaseException;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import com.lelegspears.project_wev_services.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO findById(Long id){
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return userMapper.toDTO(user);
    }

    public List<UserResponseDTO> findAll(){
        List<User> usersList = repository.findAll();
        return userMapper.toDTOList(usersList);
    }

    @Transactional
    public UserResponseDTO insert(UserCreateDTO userDTO){
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = repository.save(user);
        return userMapper.toDTO(userSaved);
    }

    @Transactional
    public void deleteById(Long id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation: cannot delete entity");
        }
    }

    @Transactional
    public UserResponseDTO updateById(Long id, UserUpdateDTO newDataDTO) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        userMapper.updateEntityFromDTO(newDataDTO, user);
        if (newDataDTO.getPassword() != null && !newDataDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(newDataDTO.getPassword()));
        }
        return userMapper.toDTO(user);
    }
    }
