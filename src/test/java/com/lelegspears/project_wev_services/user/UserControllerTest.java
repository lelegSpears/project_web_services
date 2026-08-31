package com.lelegspears.project_wev_services.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lelegspears.project_wev_services.exception.handler.ResourceExceptionHandler;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import com.lelegspears.project_wev_services.infra.security.jwt.JwtService;
import com.lelegspears.project_wev_services.user.controller.UserController;
import com.lelegspears.project_wev_services.user.dtos.UserCreateDTO;
import com.lelegspears.project_wev_services.user.dtos.UserResponseDTO;
import com.lelegspears.project_wev_services.user.dtos.UserUpdateDTO;
import com.lelegspears.project_wev_services.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ResourceExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long id;

    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {

        // Arrange
        id = 1L;

        userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(id);
        userResponseDTO.setUsername("Leandro");
        userResponseDTO.setEmail("leandro@email.com");
    }

    @Test
    void findById_ShouldReturnUserDTO_WhenUserExists() throws Exception {

        given(service.findById(id))
                .willReturn(userResponseDTO);

        mockMvc.perform(
                        get("/users/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("Leandro"))
                .andExpect(jsonPath("$.email").value("leandro@email.com"));

        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void findById_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {

        Long id = 99L;

        given(service.findById(id))
                .willThrow(new ResourceNotFoundException(id));

        mockMvc.perform(
                        get("/users/{id}", id)
                )
                .andExpect(status().isNotFound());

        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void findAll_ShouldReturnUsers_WhenUsersExist() throws Exception {

        mockMvc.perform(
                        get("/users")
                )
                .andExpect(status().isOk());

        verify(service).findAll(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void createUser_ShouldReturnCreated_WhenDataIsValid() throws Exception {

        UserCreateDTO userCreateDTO = new UserCreateDTO();

        userCreateDTO.setUsername("Leandro");
        userCreateDTO.setEmail("leandro@email.com");
        userCreateDTO.setPhone("11999999999");
        userCreateDTO.setPassword("123456");

        given(service.insert(any(UserCreateDTO.class)))
                .willReturn(userResponseDTO);

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreateDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("Leandro"))
                .andExpect(jsonPath("$.email").value("leandro@email.com"));

        verify(service).insert(any(UserCreateDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void createUser_ShouldReturnBadRequest_WhenDataIsInvalid() throws Exception {

        UserCreateDTO userCreateDTO = new UserCreateDTO();

        userCreateDTO.setUsername("");
        userCreateDTO.setEmail("email-invalido");
        userCreateDTO.setPhone("");
        userCreateDTO.setPassword("");

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userCreateDTO))
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void deleteById_ShouldReturnNoContent_WhenUserExists() throws Exception {

        willDoNothing()
                .given(service)
                .deleteById(id);

        mockMvc.perform(
                        delete("/users/{id}", id)
                )
                .andExpect(status().isNoContent());

        verify(service).deleteById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void deleteById_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {

        Long id = 99L;

        willThrow(new ResourceNotFoundException(id))
                .given(service)
                .deleteById(id);

        mockMvc.perform(
                        delete("/users/{id}", id)
                )
                .andExpect(status().isNotFound());

        verify(service).deleteById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void partialUpdateById_ShouldReturnUpdatedUser_WhenDataIsValid() throws Exception {

        UserUpdateDTO updateDTO = new UserUpdateDTO();

        updateDTO.setUsername("Leandro Atualizado");
        updateDTO.setEmail("novo@email.com");
        updateDTO.setPhone("11888888888");

        UserResponseDTO updatedUser = new UserResponseDTO();

        updatedUser.setId(id);
        updatedUser.setUsername("Leandro Atualizado");
        updatedUser.setEmail("novo@email.com");

        given(
                service.partialUpdateById(
                        eq(id),
                        any(UserUpdateDTO.class)
                )
        ).willReturn(updatedUser);

        mockMvc.perform(
                        patch("/users/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDTO))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("Leandro Atualizado"))
                .andExpect(jsonPath("$.email").value("novo@email.com"));

        verify(service).partialUpdateById(
                eq(id),
                any(UserUpdateDTO.class)
        );

        verifyNoMoreInteractions(service);
    }

    @Test
    void partialUpdateById_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {

        UserUpdateDTO updateDTO = new UserUpdateDTO();

        updateDTO.setUsername("Leandro Atualizado");

        Long id = 99L;

        given(
                service.partialUpdateById(
                        eq(id),
                        any(UserUpdateDTO.class)
                )
        ).willThrow(new ResourceNotFoundException(id));

        mockMvc.perform(
                        patch("/users/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDTO))
                )
                .andExpect(status().isNotFound());

        verify(service).partialUpdateById(
                eq(id),
                any(UserUpdateDTO.class)
        );

        verifyNoMoreInteractions(service);
    }

    @Test
    void promoteToAdmin_ShouldReturnUpdatedUser_WhenUserExists() throws Exception {

        given(service.promoteToAdmin(id))
                .willReturn(userResponseDTO);

        mockMvc.perform(
                        patch("/users/promote/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("Leandro"));

        verify(service).promoteToAdmin(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void demoteToUser_ShouldReturnUpdatedUser_WhenUserExists() throws Exception {

        given(service.demoteToUser(id))
                .willReturn(userResponseDTO);

        mockMvc.perform(
                        patch("/users/demote/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("Leandro"));

        verify(service).demoteToUser(id);
        verifyNoMoreInteractions(service);
    }
}