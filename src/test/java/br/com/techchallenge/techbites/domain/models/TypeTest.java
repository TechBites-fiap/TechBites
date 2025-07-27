package br.com.techchallenge.techbites.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    @DisplayName("GIVEN active true WHEN isInactive THEN should return false")
    void testIsInactive_ActiveTrue() {
        // GIVEN
        Type type = new Type();
        type.setActive(true);

        // WHEN
        boolean result = type.isInactive();

        // THEN
        assertFalse(result);
    }

    @Test
    @DisplayName("GIVEN active false WHEN isInactive THEN should return true")
    void testIsInactive_ActiveFalse() {
        // GIVEN
        Type type = new Type();
        type.setActive(false);

        // WHEN
        boolean result = type.isInactive();

        // THEN
        assertTrue(result);
    }

    @Test
    @DisplayName("GIVEN a new Type WHEN set and get all fields THEN should return expected values")
    void testGettersAndSetters() {
        // GIVEN
        Type type = new Type();
        Long id = 123L;
        String typeName = "Entrada";
        Boolean active = true;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime lastUpdatedAt = LocalDateTime.now();

        // WHEN
        type.setId(id);
        type.setType(typeName);
        type.setActive(active);
        type.setCreatedAt(createdAt);
        type.setLastUpdatedAt(lastUpdatedAt);

        // THEN
        assertEquals(id, type.getId());
        assertEquals(typeName, type.getType());
        assertEquals(active, type.getActive());
        assertEquals(createdAt, type.getCreatedAt());
        assertEquals(lastUpdatedAt, type.getLastUpdatedAt());
    }
}

