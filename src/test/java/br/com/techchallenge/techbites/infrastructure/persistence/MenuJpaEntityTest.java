package br.com.techchallenge.techbites.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuJpaEntityTest {

    @Test
    @DisplayName("Deve testar getters e setters")
    void testGettersAndSetters() {
        MenuJpaEntity entity = new MenuJpaEntity();
        Long id = 1L;
        String name = "Menu Entidade";
        String description = "Descrição da Entidade";
        RestaurantJpaEntity restaurant = new RestaurantJpaEntity();
        List<MenuItemJpaEntity> items = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Boolean active = true;

        entity.setId(id);
        entity.setName(name);
        entity.setDescription(description);
        entity.setRestaurant(restaurant);
        entity.setItems(items);
        entity.setCreatedAt(now);
        entity.setLastUpdatedAt(now);
        entity.setActive(active);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
        assertEquals(restaurant, entity.getRestaurant());
        assertEquals(items, entity.getItems());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getLastUpdatedAt());
        assertEquals(active, entity.getActive());
    }

    @Test
    @DisplayName("Deve testar a lógica de equals e hashCode com 100% de cobertura")
    void testEqualsAndHashCode() {
        MenuJpaEntity entity1 = new MenuJpaEntity();
        entity1.setId(1L);

        MenuJpaEntity entity2 = new MenuJpaEntity();
        entity2.setId(1L);

        MenuJpaEntity entity3 = new MenuJpaEntity();
        entity3.setId(2L);

        MenuJpaEntity entity4WithNullId = new MenuJpaEntity();
        MenuJpaEntity entity5WithNullId = new MenuJpaEntity();

        assertTrue(entity1.equals(entity1));
        assertTrue(entity1.equals(entity2));
        assertTrue(entity2.equals(entity1));

        assertFalse(entity1.equals(entity3));
        assertFalse(entity1.equals(null));
        assertFalse(entity1.equals(new Object()));
        assertFalse(entity1.equals(entity4WithNullId));
        assertTrue(entity4WithNullId.equals(entity5WithNullId));

        assertEquals(entity1.hashCode(), entity2.hashCode());
        assertNotEquals(entity1.hashCode(), entity3.hashCode());
        assertEquals(entity4WithNullId.hashCode(), entity5WithNullId.hashCode());
    }
}