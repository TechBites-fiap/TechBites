package br.com.techchallenge.techbites.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import br.com.techchallenge.techbites.domain.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("GIVEN new User WHEN get/set THEN all fields must match")
    void testGettersAndSetters() {
        User user = new User(
                1L,
                "João Silva",
                "joao.silva@email",
                "senha123",
                Role.USER,
                LocalDateTime.of(2023, 7, 27, 10, 0),
                LocalDateTime.of(2023, 7, 27, 12, 0),
                true
        );

        assertEquals(1L, user.getId());
        assertEquals("João Silva", user.getName());
        assertEquals("joao.silva@email", user.getEmail());
        assertEquals("senha123", user.getPassword());
        assertEquals(Role.USER, user.getRole());
        assertEquals(LocalDateTime.of(2023, 7, 27, 10, 0), user.getCreatedAt());
        assertEquals(LocalDateTime.of(2023, 7, 27, 12, 0), user.getLastUpdatedAt());
        assertTrue(user.getActive());

        user.setId(2L);
        user.setName("Pedro");
        user.setEmail("pedro@email");
        user.setPassword("novaSenha");
        user.setRole(Role.ADMIN);
        LocalDateTime createdAtNew = LocalDateTime.of(2023, 7, 28, 9, 0);
        LocalDateTime lastUpdatedNew = LocalDateTime.of(2023, 7, 28, 10, 0);
        user.setCreatedAt(createdAtNew);
        user.setLastUpdatedAt(lastUpdatedNew);
        user.setActive(false);

        assertEquals(2L, user.getId());
        assertEquals("Pedro", user.getName());
        assertEquals("pedro@email", user.getEmail());
        assertEquals("novaSenha", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals(createdAtNew, user.getCreatedAt());
        assertEquals(lastUpdatedNew, user.getLastUpdatedAt());
        assertFalse(user.getActive());
    }

    @Test
    @DisplayName("GIVEN user active flag WHEN isInactive THEN return negation of active")
    void testIsInactive() {
        User user = new User("João", "joao@email", "senha", Role.USER);

        user.setActive(true);
        assertFalse(user.isInactive());

        user.setActive(false);
        assertTrue(user.isInactive());
    }

    @Test
    @DisplayName("GIVEN user WHEN update THEN fields updated and lastUpdatedAt refreshed")
    void testUpdate() {
        User user = new User("João", "joao@email", "senha", Role.USER);

        LocalDateTime beforeUpdate = user.getLastUpdatedAt();
        if (beforeUpdate == null) {
            beforeUpdate = LocalDateTime.MIN;
        }

        try { Thread.sleep(10); } catch (InterruptedException ignored) {}

        user.update("Pedro", "pedro@email", "novaSenha", Role.ADMIN);

        assertEquals("Pedro", user.getName());
        assertEquals("pedro@email", user.getEmail());
        assertEquals("novaSenha", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
        assertTrue(user.getLastUpdatedAt().isAfter(beforeUpdate));
    }

    @Test
    @DisplayName("GIVEN user WHEN deactivate THEN active flag false")
    void testDeactivate() {
        User user = new User("João", "joao@email", "senha", Role.USER);

        user.setActive(true);
        user.deactivate();
        assertFalse(user.getActive());
    }
}


