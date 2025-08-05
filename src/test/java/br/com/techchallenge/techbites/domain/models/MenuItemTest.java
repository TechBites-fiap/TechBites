package br.com.techchallenge.techbites.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem();
    }

    @Test
    @DisplayName("Deve retornar true para isInactive quando active for false")
    void isInactive_ShouldReturnTrue_WhenActiveIsFalse() {
        menuItem.setActive(false); //
        assertTrue(menuItem.isInactive()); //
    }

    @Test
    @DisplayName("Deve retornar false para isInactive quando active for true")
    void isInactive_ShouldReturnFalse_WhenActiveIsTrue() {
        menuItem.setActive(true); //
        assertFalse(menuItem.isInactive()); //
    }

    @Test
    @DisplayName("Deve testar getters e setters")
    void testGettersAndSetters() {
        Long id = 1L;
        String name = "Hambúrguer";
        String description = "Pão, carne e queijo";
        BigDecimal price = new BigDecimal("25.50");
        String picturePath = "/img/burger.png";
        Menu menu = new Menu();
        Type itemType = new Type();
        LocalDateTime now = LocalDateTime.now();
        Boolean active = true;

        menuItem.setId(id); //
        menuItem.setName(name); //
        menuItem.setDescription(description); //
        menuItem.setPrice(price); //
        menuItem.setPicturePath(picturePath); //
        menuItem.setMenu(menu); //
        menuItem.setItemType(itemType); //
        menuItem.setCreatedAt(now); //
        menuItem.setLastUpdatedAt(now); //
        menuItem.setActive(active); //

        assertEquals(id, menuItem.getId()); //
        assertEquals(name, menuItem.getName()); //
        assertEquals(description, menuItem.getDescription()); //
        assertEquals(price, menuItem.getPrice()); //
        assertEquals(picturePath, menuItem.getPicturePath()); //
        assertEquals(menu, menuItem.getMenu()); //
        assertEquals(itemType, menuItem.getItemType()); //
        assertEquals(now, menuItem.getCreatedAt()); //
        assertEquals(now, menuItem.getLastUpdatedAt()); //
        assertEquals(active, menuItem.getActive()); //
    }

    @Test
    @DisplayName("Deve testar o construtor com todos os argumentos")
    void testAllArgsConstructor() {
        Long id = 1L;
        String name = "Pizza";
        String description = "Molho, queijo e calabresa";
        BigDecimal price = new BigDecimal("40.00");
        String picturePath = "/img/pizza.png";
        Menu menu = new Menu();
        Type itemType = new Type();
        LocalDateTime now = LocalDateTime.now();
        Boolean active = true;

        MenuItem fullMenuItem = new MenuItem(id, name, description, price, picturePath, menu, itemType, now, now, active); //

        assertEquals(id, fullMenuItem.getId()); //
        assertEquals(name, fullMenuItem.getName()); //
        assertEquals(description, fullMenuItem.getDescription()); //
        assertEquals(price, fullMenuItem.getPrice()); //
        assertEquals(picturePath, fullMenuItem.getPicturePath()); //
        assertEquals(menu, fullMenuItem.getMenu()); //
        assertEquals(itemType, fullMenuItem.getItemType()); //
        assertEquals(now, fullMenuItem.getCreatedAt()); //
        assertEquals(now, fullMenuItem.getLastUpdatedAt()); //
        assertEquals(active, fullMenuItem.getActive()); //
    }

    @Test
    @DisplayName("Deve testar construtor sem argumentos")
    void testNoArgsConstructor() {
        MenuItem emptyItem = new MenuItem(); //
        assertNotNull(emptyItem);
    }

    @Test
    @DisplayName("Deve testar a lógica de equals e hashCode com 100% de cobertura")
    void testEqualsAndHashCode() {
        MenuItem item1 = new MenuItem();
        item1.setId(1L);

        MenuItem item2 = new MenuItem();
        item2.setId(1L);

        MenuItem item3 = new MenuItem();
        item3.setId(2L);

        MenuItem item4WithNullId = new MenuItem();
        MenuItem item5WithNullId = new MenuItem();

        assertTrue(item1.equals(item1), "Um objeto deve ser igual a ele mesmo");

        assertTrue(item1.equals(item2), "Objetos com mesmo ID devem ser iguais");
        assertTrue(item2.equals(item1), "A igualdade deve ser simétrica");

        assertFalse(item1.equals(item3), "Objetos com IDs diferentes não devem ser iguais");

        assertFalse(item1.equals(null), "Comparação com nulo deve retornar falso");

        assertFalse(item1.equals(new Object()), "Comparação com tipo diferente deve retornar falso");

        assertFalse(item1.equals(item4WithNullId), "Um objeto com ID não pode ser igual a um com ID nulo");
        assertFalse(item4WithNullId.equals(item1), "Um objeto com ID nulo não pode ser igual a um com ID preenchido");

        assertTrue(item4WithNullId.equals(item5WithNullId), "Dois objetos diferentes com ID nulo devem ser considerados iguais pelo Objects.equals");

        assertEquals(item1.hashCode(), item2.hashCode(), "HashCode deve ser o mesmo para objetos iguais");
        assertNotEquals(item1.hashCode(), item3.hashCode(), "HashCode deve ser diferente para objetos diferentes");
        assertEquals(item4WithNullId.hashCode(), item5WithNullId.hashCode(), "HashCode de objetos com ID nulo deve ser consistente");
    }
}