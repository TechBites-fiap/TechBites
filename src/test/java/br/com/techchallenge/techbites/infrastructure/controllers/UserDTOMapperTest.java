package br.com.techchallenge.techbites.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.*;

import br.com.techchallenge.techbites.domain.enums.Role;
import br.com.techchallenge.techbites.infrastructure.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.UserUpdateRequestDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserDTOMapperTest {

    private final UserDTOMapper mapper = new UserDTOMapper();

    @Test
    @DisplayName("GIVEN null Optional WHEN toOpResponseDTO THEN return empty Optional")
    void testToOpResponseDTO_WithEmptyOptional() {
        // GIVEN
        Optional<UserJpaEntity> emptyOptional = Optional.empty();

        // WHEN
        Optional<UserResponseDTO> result = mapper.toOpResponseDTO(emptyOptional);

        // THEN
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("GIVEN valid Optional WHEN toOpResponseDTO THEN map correctly with Role.USER")
    void testToOpResponseDTO_ValidOptional() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(1L);
        entity.setName("João Silva");
        entity.setEmail("joao.silva@gmail.com");
        entity.setRole(Role.USER);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setLastUpdatedAt(LocalDateTime.now());
        entity.setActive(true);
        Optional<UserJpaEntity> optionalEntity = Optional.of(entity);

        // WHEN
        Optional<UserResponseDTO> result = mapper.toOpResponseDTO(optionalEntity);

        // THEN
        assertTrue(result.isPresent());
        UserResponseDTO dto = result.get();
        assertEquals(1L, dto.id());
        assertEquals("João Silva", dto.name());
        assertEquals("joao.silva@gmail.com", dto.email());
        assertEquals(Role.USER, dto.role());
        assertTrue(dto.active());
    }

    @Test
    @DisplayName("GIVEN valid UserRequestDTO WHEN toJpaEntity THEN map correctly with Role.USER")
    void testToJpaEntity_WithUserRequestDTO() {
        // GIVEN
        UserRequestDTO request = new UserRequestDTO("João Silva", "joao.silva@gmail.com", "senha123", Role.USER);

        // WHEN
        UserJpaEntity entity = mapper.toJpaEntity(request);

        // THEN
        assertNotNull(entity);
        assertEquals("João Silva", entity.getName());
        assertEquals("joao.silva@gmail.com", entity.getEmail());
        assertEquals("senha123", entity.getPassword());
        assertEquals(Role.USER, entity.getRole());
        assertTrue(entity.getActive());
    }

    @Test
    @DisplayName("GIVEN valid UserJpaEntity WHEN toResponseDTO THEN map correctly with Role.USER")
    void testToResponseDTO_WithEntity() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(1L);
        entity.setName("João Silva");
        entity.setEmail("joao.silva@gmail.com");
        entity.setRole(Role.USER);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setLastUpdatedAt(LocalDateTime.now());
        entity.setActive(true);

        // WHEN
        UserResponseDTO dto = mapper.toResponseDTO(entity);

        // THEN
        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("João Silva", dto.name());
        assertEquals("joao.silva@gmail.com", dto.email());
        assertEquals(Role.USER, dto.role());
        assertTrue(dto.active());
    }

    @Test
    @DisplayName("GIVEN list of UserJpaEntities WHEN toListResponseDTO THEN map all correctly with Role.USER")
    void testToListResponseDTO_WithList() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(1L);
        entity.setName("João Silva");
        entity.setEmail("joao.silva@gmail.com");
        entity.setRole(Role.USER);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setLastUpdatedAt(LocalDateTime.now());
        entity.setActive(true);
        List<UserJpaEntity> entities = List.of(entity);

        // WHEN
        List<UserResponseDTO> dtos = mapper.toListResponseDTO(entities);

        // THEN
        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals("João Silva", dtos.get(0).name());
        assertEquals(Role.USER, dtos.get(0).role());
    }

    @Test
    @DisplayName("GIVEN valid UserUpdateRequestDTO WHEN toJpaEntity THEN map correctly with Role.USER")
    void testToJpaEntity_WithUserUpdateRequestDTO() {
        // GIVEN
        UserUpdateRequestDTO request = new UserUpdateRequestDTO("João Silva", "joao.silva@gmail.com", Role.USER);

        // WHEN
        UserJpaEntity entity = mapper.toJpaEntity(request);

        // THEN
        assertNotNull(entity);
        assertEquals("João Silva", entity.getName());
        assertEquals("joao.silva@gmail.com", entity.getEmail());
        assertEquals(Role.USER, entity.getRole());
    }
}


