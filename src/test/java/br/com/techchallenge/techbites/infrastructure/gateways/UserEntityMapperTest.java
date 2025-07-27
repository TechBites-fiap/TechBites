package br.com.techchallenge.techbites.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.*;

import br.com.techchallenge.techbites.domain.enums.Role;
import br.com.techchallenge.techbites.domain.models.User;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class UserEntityMapperTest {

    private final UserEntityMapper mapper = new UserEntityMapper();

    @Test
    @DisplayName("GIVEN null entity WHEN toDomain THEN return null")
    void testToDomain_NullEntity() {
        // WHEN
        User result = mapper.toDomain(null);

        // THEN
        assertNull(result);
    }

    @Test
    @DisplayName("GIVEN valid UserJpaEntity WHEN toDomain THEN return User with matching fields")
    void testToDomain_ValidEntity() {
        // GIVEN
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(10L);
        entity.setName("João Silva");
        entity.setEmail("joao.silva@biqueira.com");
        entity.setPassword("senha123");
        entity.setRole(Role.USER);
        entity.setCreatedAt(LocalDateTime.of(2023, 7, 27, 10, 0));
        entity.setLastUpdatedAt(LocalDateTime.of(2023, 7, 27, 12, 0));
        entity.setActive(true);

        // WHEN
        User domain = mapper.toDomain(entity);

        // THEN
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getEmail(), domain.getEmail());
        assertEquals(entity.getPassword(), domain.getPassword());
        assertEquals(entity.getRole(), domain.getRole());
        assertEquals(entity.getCreatedAt(), domain.getCreatedAt());
        assertEquals(entity.getLastUpdatedAt(), domain.getLastUpdatedAt());
        assertEquals(entity.getActive(), domain.getActive());
    }

    @Test
    @DisplayName("GIVEN null domain WHEN toEntity THEN return null")
    void testToEntity_NullDomain() {
        // WHEN
        UserJpaEntity result = mapper.toEntity(null);

        // THEN
        assertNull(result);
    }

    @Test
    @DisplayName("GIVEN valid User WHEN toEntity THEN return UserJpaEntity with matching fields")
    void testToEntity_ValidDomain() {
        // GIVEN
        User domain = new User(
                20L,
                "João Silva",
                "joao.silva@email.com",
                "senha123",
                Role.USER,
                LocalDateTime.of(2023, 7, 27, 14, 0),
                LocalDateTime.of(2023, 7, 27, 16, 0),
                true
        );

        // WHEN
        UserJpaEntity entity = mapper.toEntity(domain);

        // THEN
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getEmail(), entity.getEmail());
        assertEquals(domain.getPassword(), entity.getPassword());
        assertEquals(domain.getRole(), entity.getRole());
        assertEquals(domain.getCreatedAt(), entity.getCreatedAt());
        assertEquals(domain.getLastUpdatedAt(), entity.getLastUpdatedAt());
        assertEquals(domain.getActive(), entity.getActive());
    }
}

