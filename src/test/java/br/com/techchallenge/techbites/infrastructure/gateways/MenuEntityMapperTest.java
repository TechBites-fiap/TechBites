package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuItemJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MenuEntityMapperTest {

    @Mock
    private RestaurantEntityMapper restaurantMapper;
    @Mock
    private MenuItemEntityMapper menuItemMapper;

    @InjectMocks
    private MenuEntityMapper menuEntityMapper;

    private Menu menuDomain;
    private MenuJpaEntity menuEntity;

    @BeforeEach
    void setUp() {
        Restaurant restaurantDomain = new Restaurant();
        RestaurantJpaEntity restaurantEntity = new RestaurantJpaEntity();

        MenuItem menuItemDomain = new MenuItem();
        MenuItemJpaEntity menuItemEntity = new MenuItemJpaEntity();

        menuDomain = new Menu();
        menuDomain.setId(1L);
        menuDomain.setName("Menu Domain");
        menuDomain.setRestaurant(restaurantDomain);
        menuDomain.setItems(Collections.singletonList(menuItemDomain));
        menuDomain.setActive(true);

        menuEntity = new MenuJpaEntity();
        menuEntity.setId(1L);
        menuEntity.setName("Menu Entity");
        menuEntity.setRestaurant(restaurantEntity);
        menuEntity.setItems(Collections.singletonList(menuItemEntity));
        menuEntity.setActive(true);

        // Mocks for mappers
        when(restaurantMapper.toDomain(any(RestaurantJpaEntity.class))).thenReturn(restaurantDomain);
        when(restaurantMapper.toEntity(any(Restaurant.class))).thenReturn(restaurantEntity);
        when(menuItemMapper.toDomain(any(MenuItemJpaEntity.class))).thenReturn(menuItemDomain);
        when(menuItemMapper.toEntity(any(MenuItem.class))).thenReturn(menuItemEntity);
    }

    @Test
    @DisplayName("Deve mapear de Entidade para Domínio")
    void toDomain() {
        Menu result = menuEntityMapper.toDomain(menuEntity);

        assertEquals(menuEntity.getId(), result.getId());
        assertEquals(menuEntity.getName(), result.getName());
        assertNotNull(result.getRestaurant());
        assertFalse(result.getItems().isEmpty());
    }

    @Test
    @DisplayName("Deve mapear de Entidade para Domínio com Itens Nulos")
    void toDomain_withNullItems() {
        menuEntity.setItems(null);
        Menu result = menuEntityMapper.toDomain(menuEntity);
        assertNull(result.getItems());
    }

    @Test
    @DisplayName("Deve retornar nulo ao mapear Entidade nula para Domínio")
    void toDomain_withNullEntity() {
        assertNull(menuEntityMapper.toDomain(null));
    }

    @Test
    @DisplayName("Deve mapear de Domínio para Entidade")
    void toEntity() {
        MenuJpaEntity result = menuEntityMapper.toEntity(menuDomain);

        assertEquals(menuDomain.getId(), result.getId());
        assertEquals(menuDomain.getName(), result.getName());
        assertNotNull(result.getRestaurant());
        assertFalse(result.getItems().isEmpty());
    }

    @Test
    @DisplayName("Deve mapear de Domínio para Entidade com Itens Nulos")
    void toEntity_withNullItems() {
        menuDomain.setItems(null);
        MenuJpaEntity result = menuEntityMapper.toEntity(menuDomain);
        assertNull(result.getItems());
    }

    @Test
    @DisplayName("Deve retornar nulo ao mapear Domínio nulo para Entidade")
    void toEntity_withNullDomain() {
        assertNull(menuEntityMapper.toEntity(null));
    }
}