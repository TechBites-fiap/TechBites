package br.com.techchallenge.techbites.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu();
    }

    @Test
    @DisplayName("Deve retornar true para isInactive quando active for false")
    void isInactive_ShouldReturnTrue_WhenActiveIsFalse() {
        menu.setActive(false); //
        assertTrue(menu.isInactive()); //
    }

    @Test
    @DisplayName("Deve retornar false para isInactive quando active for true")
    void isInactive_ShouldReturnFalse_WhenActiveIsTrue() {
        menu.setActive(true); //
        assertFalse(menu.isInactive()); //
    }

    @Test
    @DisplayName("Deve testar getters e setters")
    void testGettersAndSetters() {
        Long id = 1L;
        String name = "Menu de Verão";
        String description = "Pratos leves e refrescantes";
        Restaurant restaurant = new Restaurant();
        List<MenuItem> items = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Boolean active = true;

        menu.setId(id); //
        menu.setName(name); //
        menu.setDescription(description); //
        menu.setRestaurant(restaurant); //
        menu.setItems(items); //
        menu.setCreatedAt(now); //
        menu.setLastUpdatedAt(now); //
        menu.setActive(active); //

        assertEquals(id, menu.getId()); //
        assertEquals(name, menu.getName()); //
        assertEquals(description, menu.getDescription()); //
        assertEquals(restaurant, menu.getRestaurant()); //
        assertEquals(items, menu.getItems()); //
        assertEquals(now, menu.getCreatedAt()); //
        assertEquals(now, menu.getLastUpdatedAt()); //
        assertEquals(active, menu.getActive()); //
    }

    @Test
    @DisplayName("Deve testar o construtor com todos os argumentos")
    void testAllArgsConstructor() {
        Long id = 1L;
        String name = "Menu Completo";
        String description = "Descrição completa";
        Restaurant restaurant = new Restaurant();
        List<MenuItem> items = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Boolean active = true;

        Menu fullMenu = new Menu(id, name, description, restaurant, items, now, now, active); //

        assertEquals(id, fullMenu.getId()); //
        assertEquals(name, fullMenu.getName()); //
        assertEquals(description, fullMenu.getDescription()); //
        assertEquals(restaurant, fullMenu.getRestaurant()); //
        assertEquals(items, fullMenu.getItems()); //
        assertEquals(now, fullMenu.getCreatedAt()); //
        assertEquals(now, fullMenu.getLastUpdatedAt()); //
        assertEquals(active, fullMenu.getActive()); //
    }

    @Test
    @DisplayName("Deve testar construtor sem argumentos")
    void testNoArgsConstructor() {
        Menu emptyMenu = new Menu(); //
        assertNotNull(emptyMenu);
    }

    @Test
    @DisplayName("Deve testar a lógica de equals e hashCode com 100% de cobertura")
    void testEqualsAndHashCode() {
        Menu menu1 = new Menu();
        menu1.setId(1L);

        Menu menu2 = new Menu();
        menu2.setId(1L);

        Menu menu3 = new Menu();
        menu3.setId(2L);

        Menu menu4WithNullId = new Menu();
        Menu menu5WithNullId = new Menu();

        assertTrue(menu1.equals(menu1), "Um objeto deve ser igual a ele mesmo");

        assertTrue(menu1.equals(menu2), "Objetos com mesmo ID devem ser iguais");
        assertTrue(menu2.equals(menu1), "A igualdade deve ser simétrica");

        assertFalse(menu1.equals(menu3), "Objetos com IDs diferentes não devem ser iguais");

        assertFalse(menu1.equals(null), "Comparação com nulo deve retornar falso");

        assertFalse(menu1.equals(new Object()), "Comparação com tipo diferente deve retornar falso");

        assertFalse(menu1.equals(menu4WithNullId), "Um objeto com ID não pode ser igual a um com ID nulo");
        assertFalse(menu4WithNullId.equals(menu1), "Um objeto com ID nulo não pode ser igual a um com ID preenchido");

        assertTrue(menu4WithNullId.equals(menu5WithNullId), "Dois objetos diferentes com ID nulo devem ser considerados iguais pelo Objects.equals");

        assertEquals(menu1.hashCode(), menu2.hashCode(), "HashCode deve ser o mesmo para objetos iguais");
        assertNotEquals(menu1.hashCode(), menu3.hashCode(), "HashCode deve ser diferente para objetos diferentes");
        assertEquals(menu4WithNullId.hashCode(), menu5WithNullId.hashCode(), "HashCode de objetos com ID nulo deve ser consistente");
    }
}