package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.dtos.LoginRequestDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthDTOMapperTest {

    private AuthDTOMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AuthDTOMapper();
    }

    @Test
    @DisplayName("GIVEN valid LoginRequestDTO WHEN toEntity THEN should return filled UserJpaEntity")
    void testToEntity_withValidLoginRequestDTO() {
        // GIVEN
        LoginRequestDTO loginRequest = new LoginRequestDTO("user@example.com", "senha123");

        // WHEN
        UserJpaEntity result = mapper.toEntity(loginRequest);

        // THEN
        assertNotNull(result);
        assertEquals("user@example.com", result.getEmail());
        assertEquals("senha123", result.getPassword());
    }

    @Test
    @DisplayName("GIVEN null LoginRequestDTO WHEN toEntity THEN should throw NullPointerException")
    void testToEntity_withNull() {
        assertThrows(NullPointerException.class, () -> mapper.toEntity(null));
    }
}
