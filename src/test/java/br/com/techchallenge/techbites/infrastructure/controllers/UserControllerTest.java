package br.com.techchallenge.techbites.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.techchallenge.techbites.application.useCases.UserUseCase;
import br.com.techchallenge.techbites.domain.enums.Role;
import br.com.techchallenge.techbites.infrastructure.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.UserUpdateRequestDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserUseCase useCase;

    @Mock
    private UserDTOMapper mapper;

    @InjectMocks
    private UserController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GIVEN valid UserRequestDTO WHEN POST /users THEN create user and return 201")
    void testCreateUser() throws Exception {
        // GIVEN
        UserRequestDTO requestDTO = new UserRequestDTO("João Silva", "joao.silva@gmail.com", "senha123", Role.USER);
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(1L);
        entity.setName("João Silva");
        entity.setEmail("joao.silva@gmail.com");
        entity.setRole(Role.USER);
        entity.setActive(true);

        UserResponseDTO responseDTO = new UserResponseDTO(1L, "João Silva", "joao.silva@gmail.com", Role.USER, null, null, true);

        when(mapper.toJpaEntity(any(UserRequestDTO.class))).thenReturn(entity);
        when(useCase.createUser(any(UserJpaEntity.class))).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        // WHEN & THEN
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@gmail.com"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.active").value(true));

        verify(useCase, times(1)).createUser(any(UserJpaEntity.class));
        verify(mapper, times(1)).toJpaEntity(any(UserRequestDTO.class));
        verify(mapper, times(1)).toResponseDTO(any(UserJpaEntity.class));
    }

    @Test
    @DisplayName("GIVEN active filter WHEN GET /users THEN return list of users")
    void testGetAllUsers() throws Exception {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(1L);
        entity.setName("João Silva");
        entity.setEmail("joao.silva@gmail.com");
        entity.setRole(Role.USER);
        entity.setActive(true);

        UserResponseDTO responseDTO = new UserResponseDTO(1L, "João Silva", "joao.silva@gmail.com", Role.USER, null, null, true);
        List<UserJpaEntity> entityList = List.of(entity);
        List<UserResponseDTO> dtoList = List.of(responseDTO);

        when(useCase.getAllUsers(true)).thenReturn(entityList);
        when(mapper.toListResponseDTO(entityList)).thenReturn(dtoList);

        // WHEN & THEN
        mockMvc.perform(get("/users")
                        .param("active", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("João Silva"))
                .andExpect(jsonPath("$[0].role").value("USER"));

        verify(useCase, times(1)).getAllUsers(true);
        verify(mapper, times(1)).toListResponseDTO(entityList);
    }

    @Test
    @DisplayName("GIVEN valid id WHEN GET /users/{id} THEN return user")
    void testFindUserById() throws Exception {
        // GIVEN
        Long userId = 1L;
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(userId);
        entity.setName("João Silva");
        entity.setEmail("joao.silva@gmail.com");
        entity.setRole(Role.USER);
        entity.setActive(true);

        UserResponseDTO responseDTO = new UserResponseDTO(userId, "João Silva", "joao.silva@gmail.com", Role.USER, null, null, true);

        Optional<UserJpaEntity> optionalEntity = Optional.of(entity);
        Optional<UserResponseDTO> optionalResponse = Optional.of(responseDTO);

        when(useCase.findUserByID(userId)).thenReturn(optionalEntity);
        when(mapper.toOpResponseDTO(optionalEntity)).thenReturn(optionalResponse);

        // WHEN & THEN
        mockMvc.perform(get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.role").value("USER"));

        verify(useCase, times(1)).findUserByID(userId);
        verify(mapper, times(1)).toOpResponseDTO(optionalEntity);
    }

    @Test
    @DisplayName("GIVEN valid id and UserUpdateRequestDTO WHEN PUT /users/{id} THEN update user")
    void testUpdateUser() throws Exception {
        // GIVEN
        Long userId = 1L;
        UserUpdateRequestDTO updateRequest = new UserUpdateRequestDTO("João Silva", "joao.silva@gmail.com", Role.USER);

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(userId);
        entity.setName("João Silva");
        entity.setEmail("joao.silva@gmail.com");
        entity.setRole(Role.USER);

        UserResponseDTO responseDTO = new UserResponseDTO(userId, "João Silva", "joao.silva@gmail.com", Role.USER, null, null, true);

        when(mapper.toJpaEntity(any(UserUpdateRequestDTO.class))).thenReturn(entity);
        when(useCase.updateById(eq(userId), any(UserJpaEntity.class))).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        // WHEN & THEN
        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.role").value("USER"));

        verify(useCase, times(1)).updateById(eq(userId), any(UserJpaEntity.class));
        verify(mapper, times(1)).toJpaEntity(any(UserUpdateRequestDTO.class));
        verify(mapper, times(1)).toResponseDTO(any(UserJpaEntity.class));
    }

    @Test
    @DisplayName("GIVEN valid id WHEN DELETE /users/{id} THEN delete user and return 204")
    void testDeleteUser() throws Exception {
        // GIVEN
        Long userId = 1L;
        doNothing().when(useCase).deleteUserById(userId);

        // WHEN & THEN
        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());

        verify(useCase, times(1)).deleteUserById(userId);
    }

    @Test
    @DisplayName("GIVEN valid id WHEN PATCH /users/enable/{id} THEN enable user and return 204")
    void testEnableUserById() throws Exception {
        // GIVEN
        Long userId = 1L;
        doNothing().when(useCase).enableUserById(userId);

        // WHEN & THEN
        mockMvc.perform(patch("/users/enable/{id}", userId))
                .andExpect(status().isNoContent());

        verify(useCase, times(1)).enableUserById(userId);
    }

}

