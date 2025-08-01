package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.dtos.MenuDTO;
import br.com.techchallenge.techbites.dtos.MenuItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuDTOMapperTest {

    @Mock
    private MenuItemDTOMapper menuItemMapper;

    @InjectMocks
    private MenuDTOMapper menuDTOMapper;

    private Menu menu;

    @BeforeEach
    void setUp() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        menu = new Menu();
        menu.setId(10L);
        menu.setName("Menu Teste");
        menu.setDescription("Descrição Teste");
        menu.setRestaurant(restaurant);
        menu.setCreatedAt(LocalDateTime.now());
        menu.setLastUpdatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Deve mapear Menu para MenuDTO corretamente")
    void toResponseDTO_Success() {
        MenuItem menuItem = new MenuItem();
        menu.setItems(Collections.singletonList(menuItem));
        when(menuItemMapper.toResponseDTO(any(MenuItem.class))).thenReturn(new MenuItemDTO());

        MenuDTO dto = menuDTOMapper.toResponseDTO(menu);

        assertNotNull(dto);
        assertEquals(menu.getId(), dto.getIdMenu());
        assertEquals(menu.getName(), dto.getName());
        assertEquals(menu.getDescription(), dto.getDescription());
        assertEquals(menu.getRestaurant().getId(), dto.getRestaurantId());
        assertEquals(menu.getCreatedAt(), dto.getCreatedAt());
        assertEquals(menu.getLastUpdatedAt(), dto.getLastUpdatedAt());
        assertNotNull(dto.getItems());
        assertFalse(dto.getItems().isEmpty());
    }

    @Test
    @DisplayName("Deve retornar nulo se o menu for nulo")
    void toResponseDTO_NullMenu() {
        assertNull(menuDTOMapper.toResponseDTO(null));
    }

    @Test
    @DisplayName("Deve mapear corretamente quando a lista de itens for nula")
    void toResponseDTO_NullItems() {
        menu.setItems(null);

        MenuDTO dto = menuDTOMapper.toResponseDTO(menu);

        assertNotNull(dto);
        assertEquals(menu.getName(), dto.getName());
        assertNull(dto.getItems());
    }
}