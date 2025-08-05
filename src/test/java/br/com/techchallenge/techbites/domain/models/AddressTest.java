package br.com.techchallenge.techbites.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    @DisplayName("GIVEN active true WHEN isInactive THEN should return false")
    void testIsInactive_ActiveTrue() {
        // GIVEN
        Address address = new Address(
                1L,
                "Rua A",
                "123",
                "Apto 101",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "00000-000",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(),
                true
        );

        // WHEN
        boolean result = address.isInactive();

        // THEN
        assertFalse(result);
    }

    @Test
    @DisplayName("GIVEN active false WHEN isInactive THEN should return true")
    void testIsInactive_ActiveFalse() {
        // GIVEN
        Address address = new Address(
                1L,
                "Rua A",
                "123",
                "Apto 101",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "00000-000",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(),
                false
        );

        // WHEN
        boolean result = address.isInactive();

        // THEN
        assertTrue(result);
    }

    @Test
    @DisplayName("GIVEN new Address WHEN set and get all fields THEN should return expected values")
    void testGettersAndSetters() {
        // GIVEN
        Address address = new Address(
                1L,
                "Rua A",
                "123",
                "Apto 101",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "00000-000",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(),
                true
        );

        // WHEN
        address.setId(2L);
        address.setStreet("Rua B");
        address.setNumber("456");
        address.setComplement("Casa");
        address.setNeighborhood("Bairro X");
        address.setCity("Cidade Y");
        address.setState("Estado Z");
        address.setZipCode("11111-111");
        LocalDateTime createdAt = LocalDateTime.now().minusDays(5);
        LocalDateTime lastUpdatedAt = LocalDateTime.now().minusDays(2);
        address.setCreatedAt(createdAt);
        address.setLastUpdatedAt(lastUpdatedAt);
        address.setActive(false);

        // THEN
        assertEquals(2L, address.getId());
        assertEquals("Rua B", address.getStreet());
        assertEquals("456", address.getNumber());
        assertEquals("Casa", address.getComplement());
        assertEquals("Bairro X", address.getNeighborhood());
        assertEquals("Cidade Y", address.getCity());
        assertEquals("Estado Z", address.getState());
        assertEquals("11111-111", address.getZipCode());
        assertEquals(createdAt, address.getCreatedAt());
        assertEquals(lastUpdatedAt, address.getLastUpdatedAt());
        assertFalse(address.isActive());
    }
}

