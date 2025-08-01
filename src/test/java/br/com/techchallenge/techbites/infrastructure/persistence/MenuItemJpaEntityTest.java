package br.com.techchallenge.techbites.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemJpaEntityTest {

    @Test
    @DisplayName("Deve testar getters e setters")
    void testGettersAndSetters() {
        MenuItemJpaEntity entity = new MenuItemJpaEntity();
        Long id = 1L;
        String name = "Item Entidade";
        String description = "Descrição da Entidade";
        BigDecimal price = new BigDecimal("99.90");
        String picturePath = "/img/item.jpg";
        MenuJpaEntity menu = new MenuJpaEntity();
        TypeJpaEntity itemType = new TypeJpaEntity();
        LocalDateTime now = LocalDateTime.now();
        Boolean active = true;

        entity.setId(id);
        entity.setName(name);
        entity.setDescription(description);
        entity.setPrice(price);
        entity.setPicturePath(picturePath);
        entity.setMenu(menu);
        entity.setItemType(itemType);
        entity.setCreatedAt(now);
        entity.setLastUpdatedAt(now);
        entity.setActive(active);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
        assertEquals(price, entity.getPrice());
        assertEquals(picturePath, entity.getPicturePath());
        assertEquals(menu, entity.getMenu());
        assertEquals(itemType, entity.getItemType());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getLastUpdatedAt());
        assertEquals(active, entity.getActive());
    }

    @Test
    @DisplayName("Deve testar a lógica de equals e hashCode com 100% de cobertura")
    void testEqualsAndHashCode() {
        MenuItemJpaEntity entity1 = new MenuItemJpaEntity();
        entity1.setId(1L);

        MenuItemJpaEntity entity2 = new MenuItemJpaEntity();
        entity2.setId(1L);

        MenuItemJpaEntity entity3 = new MenuItemJpaEntity();
        entity3.setId(2L);

        MenuItemJpaEntity entity4WithNullId = new MenuItemJpaEntity();
        MenuItemJpaEntity entity5WithNullId = new MenuItemJpaEntity();

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