package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.MenuGateway;
import br.com.techchallenge.techbites.application.gateways.RestaurantGateway;
import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuUpdateDTO;
import br.com.techchallenge.techbites.infrastructure.gateways.RestaurantEntityMapper;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuUseCaseImpTest {

    @Mock
    private MenuGateway menuGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private RestaurantEntityMapper restaurantMapper;

    @InjectMocks
    private MenuUseCaseImp menuUseCase;

    private Restaurant restaurant;
    private Menu menu;
    private MenuCreateDTO menuCreateDTO;
    private MenuUpdateDTO menuUpdateDTO;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);

        menu = new Menu();
        menu.setId(1L);
        menu.setName("Menu Test");
        menu.setDescription("Description Test");
        menu.setRestaurant(restaurant);
        menu.setActive(true);
        menu.setCreatedAt(LocalDateTime.now());
        menu.setLastUpdatedAt(LocalDateTime.now());

        menuCreateDTO = new MenuCreateDTO();
        menuCreateDTO.setName("New Menu");
        menuCreateDTO.setDescription("New Description");
        menuCreateDTO.setRestaurantId(1L);

        menuUpdateDTO = new MenuUpdateDTO();
        menuUpdateDTO.setName("Updated Menu");
        menuUpdateDTO.setDescription("Updated Description");
    }

    @Test
    @DisplayName("Deve criar um menu com sucesso")
    void create_Success() {
        when(restaurantGateway.findRestaurantById(anyLong())).thenReturn(Optional.of(new br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity()));
        when(restaurantMapper.toDomain(any())).thenReturn(restaurant);
        when(menuGateway.save(any(Menu.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Menu createdMenu = menuUseCase.create(menuCreateDTO);

        assertNotNull(createdMenu);
        assertEquals(menuCreateDTO.getName(), createdMenu.getName());
        assertEquals(menuCreateDTO.getDescription(), createdMenu.getDescription());
        assertEquals(restaurant, createdMenu.getRestaurant());
        assertTrue(createdMenu.getActive());
        verify(restaurantGateway).findRestaurantById(1L);
        verify(menuGateway).save(any(Menu.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar criar menu para restaurante inexistente")
    void create_RestaurantNotFound() {
        when(restaurantGateway.findRestaurantById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuUseCase.create(menuCreateDTO));
        verify(restaurantGateway).findRestaurantById(1L);
        verify(menuGateway, never()).save(any(Menu.class));
    }

    @Test
    @DisplayName("Deve encontrar um menu pelo ID com sucesso")
    void findById_Success() {
        when(menuGateway.findById(1L)).thenReturn(Optional.of(menu));

        Menu foundMenu = menuUseCase.findById(1L);

        assertNotNull(foundMenu);
        assertEquals(menu.getId(), foundMenu.getId());
        verify(menuGateway).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar encontrar um menu com ID inexistente")
    void findById_NotFound() {
        when(menuGateway.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuUseCase.findById(1L));
        verify(menuGateway).findById(1L);
    }

    @Test
    @DisplayName("Deve encontrar menus pelo ID do restaurante")
    void findByRestaurantId_Success() {
        when(menuGateway.findByRestaurantId(1L)).thenReturn(Collections.singletonList(menu));

        List<Menu> menus = menuUseCase.findByRestaurantId(1L);

        assertNotNull(menus);
        assertFalse(menus.isEmpty());
        assertEquals(1, menus.size());
        assertEquals(menu, menus.get(0));
        verify(menuGateway).findByRestaurantId(1L);
    }

    @Test
    @DisplayName("Deve retornar lista vazia se não encontrar menus para o ID do restaurante")
    void findByRestaurantId_NotFound() {
        when(menuGateway.findByRestaurantId(1L)).thenReturn(Collections.emptyList());

        List<Menu> menus = menuUseCase.findByRestaurantId(1L);

        assertNotNull(menus);
        assertTrue(menus.isEmpty());
        verify(menuGateway).findByRestaurantId(1L);
    }

    @Test
    @DisplayName("Deve obter todos os menus ativos")
    void getAll_Active() {
        when(menuGateway.findAll(true)).thenReturn(Collections.singletonList(menu));

        List<Menu> menus = menuUseCase.getAll(true);

        assertNotNull(menus);
        assertFalse(menus.isEmpty());
        verify(menuGateway).findAll(true);
    }

    @Test
    @DisplayName("Deve obter todos os menus (ativos e inativos)")
    void getAll_All() {
        when(menuGateway.findAll(null)).thenReturn(Collections.singletonList(menu));

        List<Menu> menus = menuUseCase.getAll(null);

        assertNotNull(menus);
        assertFalse(menus.isEmpty());
        verify(menuGateway).findAll(null);
    }


    @Test
    @DisplayName("Deve atualizar um menu com sucesso")
    void update_Success() {
        when(menuGateway.findById(1L)).thenReturn(Optional.of(menu));
        when(menuGateway.save(any(Menu.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Menu updatedMenu = menuUseCase.update(1L, menuUpdateDTO);

        assertNotNull(updatedMenu);
        assertEquals(menuUpdateDTO.getName(), updatedMenu.getName());
        assertEquals(menuUpdateDTO.getDescription(), updatedMenu.getDescription());
        verify(menuGateway).findById(1L);
        verify(menuGateway).save(any(Menu.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar um menu inativo")
    void update_InactiveMenu() {
        menu.setActive(false);
        when(menuGateway.findById(1L)).thenReturn(Optional.of(menu));

        assertThrows(ResourceNotFoundException.class, () -> menuUseCase.update(1L, menuUpdateDTO));
        verify(menuGateway).findById(1L);
        verify(menuGateway, never()).save(any(Menu.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar um menu inexistente")
    void update_NotFound() {
        when(menuGateway.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuUseCase.update(1L, menuUpdateDTO));
        verify(menuGateway).findById(1L);
        verify(menuGateway, never()).save(any(Menu.class));
    }

    @Test
    @DisplayName("Deve deletar um menu com sucesso")
    void delete_Success() {
        when(menuGateway.findById(1L)).thenReturn(Optional.of(menu));
        doNothing().when(menuGateway).delete(menu);

        assertDoesNotThrow(() -> menuUseCase.delete(1L));

        verify(menuGateway).findById(1L);
        verify(menuGateway).delete(menu);
    }

    @Test
    @DisplayName("Não deve fazer nada ao tentar deletar um menu já inativo")
    void delete_AlreadyInactive() {
        menu.setActive(false);
        when(menuGateway.findById(1L)).thenReturn(Optional.of(menu));

        menuUseCase.delete(1L);

        verify(menuGateway).findById(1L);
        verify(menuGateway, never()).delete(any(Menu.class));
    }

    @Test
    @DisplayName("Deve habilitar um menu com sucesso")
    void enableById_Success() {
        menu.setActive(false);
        when(menuGateway.findById(1L)).thenReturn(Optional.of(menu));
        when(menuGateway.save(any(Menu.class))).thenAnswer(invocation -> invocation.getArgument(0));

        menuUseCase.enableById(1L);

        assertTrue(menu.getActive());
        verify(menuGateway).findById(1L);
        verify(menuGateway).save(menu);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar habilitar um menu inexistente")
    void enableById_NotFound() {
        when(menuGateway.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuUseCase.enableById(1L));

        verify(menuGateway).findById(1L);
        verify(menuGateway, never()).save(any(Menu.class));
    }
}