package com.lelegspears.project_wev_services.user;

import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import com.lelegspears.project_wev_services.user.dtos.UserResponseDTO;
import com.lelegspears.project_wev_services.user.entity.User;
import com.lelegspears.project_wev_services.user.mapper.UserMapper;
import com.lelegspears.project_wev_services.user.repository.UserRepository;
import com.lelegspears.project_wev_services.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    private Long id;

    private User user;

    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        id = 1L;

        user = new User(
                id,
                "Leandro",
                "leandro@email.com",
                "11999999999",
                "123456"
        );

        userResponseDTO  = new UserResponseDTO();

        userResponseDTO.setId(id);
        userResponseDTO.setName("Leandro");
        userResponseDTO.setEmail("leandro@email.com");

    }

    @Test
    void findById_ShouldReturnUserDTO_WhenUserExists() {

        given(repository.findById(id)).willReturn(Optional.of(user));
        given(mapper.toDTO(user)).willReturn(userResponseDTO);

        UserResponseDTO result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Leandro", result.getName());
        assertEquals("leandro@email.com", result.getEmail());

        verify(repository).findById(id);
        verify(mapper).toDTO(user);

        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {


        Long nonExistId = 99L;

        given(repository.findById(nonExistId)).willReturn(Optional.empty());


        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(nonExistId)
        );

        assertEquals("User not found", ex.getMessage());

        verify(repository).findById(nonExistId);

        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(repository);
    }
}