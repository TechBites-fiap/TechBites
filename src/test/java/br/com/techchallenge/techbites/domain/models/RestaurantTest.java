package br.com.techchallenge.techbites.domain.models;

import br.com.techchallenge.techbites.domain.enums.Role;
import br.com.techchallenge.techbites.application.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;
    private User owner;

    @BeforeEach
    void setUp() {
        owner = new User(
                1L,
                "João Silva",
                "joao@email.com",
                "senha123",
                Role.USER_RESTAURANT,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(),
                true
        );

        restaurant = new Restaurant(
                10L,
                "Boteco do João",
                "Bar raiz na quebrada",
                new Address(
                        1L ,"Rua das Lajes", "100", "Fundos", "Jardim Zona Leste",
                        "São Paulo", "SP", "01234-000",
                        LocalDateTime.now().minusDays(5),
                        LocalDateTime.now(), true
                ),
                "Brasileira",
                "18:00",
                owner,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now(),
                true
        );
    }

    @Test
    @DisplayName("GIVEN active restaurant WHEN isInactive THEN return false")
    void testIsInactive_WhenActive() {
        // GIVEN + WHEN
        boolean result = restaurant.isInactive();

        // THEN
        assertFalse(result);
    }

    @Test
    @DisplayName("GIVEN inactive restaurant WHEN isInactive THEN return true")
    void testIsInactive_WhenInactive() {
        // GIVEN
        restaurant.setActive(false);

        // WHEN
        boolean result = restaurant.isInactive();

        // THEN
        assertTrue(result);
    }

    @Test
    @DisplayName("GIVEN valid owner with USER_RESTAURANT role WHEN validateOwnerRestaurantRole THEN do nothing")
    void testValidateOwnerRestaurantRole_WithValidRole() {
        // GIVEN + WHEN + THEN
        assertDoesNotThrow(() -> restaurant.validateOwnerRestaurantRole("create"));
    }

    @Test
    @DisplayName("GIVEN owner with invalid role WHEN validateOwnerRestaurantRole THEN throw UnauthorizedException")
    void testValidateOwnerRestaurantRole_WithInvalidRole() {
        // GIVEN
        owner.setRole(Role.USER);

        // WHEN + THEN
        UnauthorizedException ex = assertThrows(
                UnauthorizedException.class,
                () -> restaurant.validateOwnerRestaurantRole("CREATE")
        );

    }

    @Test
    @DisplayName("GIVEN null owner WHEN validateOwnerRestaurantRole THEN throw UnauthorizedException")
    void testValidateOwnerRestaurantRole_WithNullOwner() {
        // GIVEN
        restaurant.setOwner(null);

        // WHEN + THEN
        assertThrows(AssertionError.class, () -> restaurant.validateOwnerRestaurantRole("create"));
    }

    @Test
    @DisplayName("GIVEN Restaurant object WHEN using getters THEN return correct values")
    void testGetters() {
        assertEquals(10L, restaurant.getId());
        assertEquals("Boteco do João", restaurant.getName());
        assertEquals("Bar raiz na quebrada", restaurant.getDescription());
        assertEquals("Brasileira", restaurant.getCuisine());
        assertEquals("18:00", restaurant.getOpeningHour());
        assertEquals(owner, restaurant.getOwner());
        assertTrue(restaurant.getActive());
    }

    @Test
    @DisplayName("GIVEN Restaurant object WHEN using setters THEN values are updated")
    void testSetters() {
        // WHEN
        restaurant.setName("Bar do Zé");
        restaurant.setCuisine("Japonesa");
        restaurant.setOpeningHour("20:00");

        // THEN
        assertEquals("Bar do Zé", restaurant.getName());
        assertEquals("Japonesa", restaurant.getCuisine());
        assertEquals("20:00", restaurant.getOpeningHour());
    }



}
