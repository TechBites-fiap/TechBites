package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.MenuUseCase;
import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuDTO;
import br.com.techchallenge.techbites.dtos.MenuUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private MenuUseCase menuUseCase;

    @Mock
    private MenuDTOMapper menuMapper;

    @InjectMocks
    private MenuController menuController;

    private Menu menu;
    private MenuDTO menuDTO;
    private MenuCreateDTO menuCreateDTO;
    private MenuUpdateDTO menuUpdateDTO;

    @BeforeEach
    void setUp() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        menu = new Menu();
        menu.setId(1L);
        menu.setName("Menu Principal");
        menu.setDescription("Descrição do menu");
        menu.setRestaurant(restaurant);
        menu.setCreatedAt(LocalDateTime.now());
        menu.setLastUpdatedAt(LocalDateTime.now());

        menuDTO = new MenuDTO();
        menuDTO.setIdMenu(1L);
        menuDTO.setName("Menu Principal");

        menuCreateDTO = new MenuCreateDTO();
        menuUpdateDTO = new MenuUpdateDTO();
    }

    @Test
    @DisplayName("Deve criar um menu e retornar status 201 Created")
    void create() {
        when(menuUseCase.create(any(MenuCreateDTO.class))).thenReturn(menu);
        when(menuMapper.toResponseDTO(any(Menu.class))).thenReturn(menuDTO);

        ResponseEntity<MenuDTO> response = menuController.create(menuCreateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(menuDTO, response.getBody());
        verify(menuUseCase).create(menuCreateDTO);
        verify(menuMapper).toResponseDTO(menu);
    }

    @Test
    @DisplayName("Deve encontrar um menu pelo ID e retornar status 200 OK")
    void findById() {
        when(menuUseCase.findById(anyLong())).thenReturn(menu);
        when(menuMapper.toResponseDTO(any(Menu.class))).thenReturn(menuDTO);

        ResponseEntity<MenuDTO> response = menuController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menuDTO, response.getBody());
        verify(menuUseCase).findById(1L);
        verify(menuMapper).toResponseDTO(menu);
    }

    @Test
    @DisplayName("Deve retornar todos os menus e status 200 OK")
    void getAll() {
        when(menuUseCase.getAll(any())).thenReturn(Collections.singletonList(menu));
        when(menuMapper.toResponseDTO(any(Menu.class))).thenReturn(menuDTO);

        ResponseEntity<List<MenuDTO>> response = menuController.getAll(true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).isEmpty());
        assertEquals(menuDTO, response.getBody().get(0));
        verify(menuUseCase).getAll(true);
        verify(menuMapper).toResponseDTO(menu);
    }

    @Test
    @DisplayName("Deve encontrar menus pelo ID do restaurante e retornar status 200 OK")
    void findByRestaurantId() {
        when(menuUseCase.findByRestaurantId(anyLong())).thenReturn(Collections.singletonList(menu));
        when(menuMapper.toResponseDTO(any(Menu.class))).thenReturn(menuDTO);

        ResponseEntity<List<MenuDTO>> response = menuController.findByRestaurantId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).isEmpty());
        assertEquals(menuDTO, response.getBody().get(0));
        verify(menuUseCase).findByRestaurantId(1L);
        verify(menuMapper).toResponseDTO(menu);
    }

    @Test
    @DisplayName("Deve atualizar um menu e retornar status 200 OK")
    void update() {
        when(menuUseCase.update(anyLong(), any(MenuUpdateDTO.class))).thenReturn(menu);
        when(menuMapper.toResponseDTO(any(Menu.class))).thenReturn(menuDTO);

        ResponseEntity<MenuDTO> response = menuController.update(1L, menuUpdateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menuDTO, response.getBody());
        verify(menuUseCase).update(1L, menuUpdateDTO);
        verify(menuMapper).toResponseDTO(menu);
    }

    @Test
    @DisplayName("Deve deletar um menu e retornar status 204 No Content")
    void deleteTest() {
        doNothing().when(menuUseCase).delete(anyLong());

        ResponseEntity<Void> response = menuController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(menuUseCase).delete(1L);
    }

    @Test
    @DisplayName("Deve habilitar um menu e retornar status 204 No Content")
    void enableById() {
        doNothing().when(menuUseCase).enableById(anyLong());

        ResponseEntity<Void> response = menuController.enableById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(menuUseCase).enableById(1L);
    }
}