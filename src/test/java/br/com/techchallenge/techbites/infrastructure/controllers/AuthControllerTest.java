package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.AuthUseCase;
import br.com.techchallenge.techbites.infrastructure.dtos.ChangePasswordDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.LoginRequestDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthUseCase authUseCase;

    @MockBean
    private AuthDTOMapper mapper;

    @Test
    @DisplayName("GIVEN valid credentials WHEN login THEN return 200 OK")
    void testValidateLoginSuccess() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO("example@email.com", "senha123");
        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail("example@email.com");
        entity.setPassword("senha123");

        when(mapper.toEntity(any())).thenReturn(entity);
        when(authUseCase.validateLogin(any())).thenReturn(true);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    @DisplayName("GIVEN invalid credentials WHEN login THEN return 401 Unauthorized")
    void testValidateLoginFailure() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO("theu@biqueira.com", "errada");
        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail("theu@biqueira.com");
        entity.setPassword("errada");

        when(mapper.toEntity(any())).thenReturn(entity);
        when(authUseCase.validateLogin(any())).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("E-mail ou senha inválidos."));
    }

    @Test
    @DisplayName("GIVEN valid ChangePasswordDTO WHEN changePassword THEN return 204 No Content")
    void testChangePassword() throws Exception {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(
                "example@email.com",
                "senha123",
                "novaSenha123",
                "novaSenha123"
        );

        mockMvc.perform(post("/auth/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(changePasswordDTO)))
                .andExpect(status().isNoContent());

        verify(authUseCase).changePassword(any(ChangePasswordDTO.class));
    }
}

