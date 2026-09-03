package com.lelegspears.project_wev_services.user.service;

import com.lelegspears.project_wev_services.infra.security.enums.Role;
import com.lelegspears.project_wev_services.user.entity.User;
import com.lelegspears.project_wev_services.user.dtos.UserCreateDTO;
import com.lelegspears.project_wev_services.user.dtos.UserResponseDTO;
import com.lelegspears.project_wev_services.user.dtos.UserUpdateDTO;
import com.lelegspears.project_wev_services.user.mapper.UserMapper;
import com.lelegspears.project_wev_services.exception.service.DatabaseException;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import com.lelegspears.project_wev_services.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.info("User found with Id:{}", id);
        return userMapper.toDTO(user);
    }

    public Page<UserResponseDTO> findAll(Pageable pageable){
        Page<User> usersList = repository.findAll(pageable);
        log.debug("Users Page found: [ page: {}, size: {}, total elements: {} ]",
                usersList.getNumber(),
                usersList.getSize(),
                usersList.getTotalElements());
        return usersList.map(userMapper::toDTO);
    }

    @Transactional
    public UserResponseDTO insert(UserCreateDTO userDTO){
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = repository.save(user);
        log.info("User Created with: [ Id:{} username: {} ]", userSaved.getId(), userSaved.getUsername());
        return userMapper.toDTO(userSaved);
    }

    @Transactional
    public void deleteById(Long id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            repository.deleteById(id);
            repository.flush();
            log.info("User with Id:{} Deleted by {}", id,  authentication.getName());
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation: cannot delete entity");
        }
    }

    @Transactional
    public UserResponseDTO partialUpdateById(Long id, UserUpdateDTO newDataDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        userMapper.updateEntityFromDTO(newDataDTO, user);
        log.info("User with: [ Id: {} Username{} ] Updated by {}", id, user.getUsername(), authentication.getName());
        if (newDataDTO.getPassword() != null && !newDataDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(newDataDTO.getPassword()));
        }
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO promoteToAdmin(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        user.setRole(Role.ADMIN);
        User promotedUser = repository.save(user);
        log.info("User with Id: {} Promoted by {}", id, authentication.getName());
        return userMapper.toDTO(promotedUser);
    }

    @Transactional
    public UserResponseDTO demoteToUser(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        user.setRole(Role.USER);
        User promotedUser = repository.save(user);
        log.info("User with Id: {} Demoted by {}", id, authentication.getName());
        return userMapper.toDTO(promotedUser);
    }
    }
