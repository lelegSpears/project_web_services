package com.lelegspears.project_wev_services.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lelegspears.project_wev_services.exception.handler.ResourceExceptionHandler;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import com.lelegspears.project_wev_services.user.controller.UserController;
import com.lelegspears.project_wev_services.user.dtos.UserResponseDTO;
import com.lelegspears.project_wev_services.user.entity.User;
import com.lelegspears.project_wev_services.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(ResourceExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Long id;

    private User user;

    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        // arrange
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
    void findById_ShouldReturnUserDTO_WhenUserExists() throws Exception {

        given(service.findById(id)).willReturn(userResponseDTO);

        // act + assert
        mockMvc.perform(get("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Leandro"))
                .andExpect(jsonPath("$.email").value("leandro@email.com"));

        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }
    @Test
    void findById_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {

        Long id = 99L;

        given(service.findById(id))
                .willThrow(new ResourceNotFoundException(id));

        mockMvc.perform(get("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }
}           // Focar estudos em Spring Security e depois retornar para aprofundar testes