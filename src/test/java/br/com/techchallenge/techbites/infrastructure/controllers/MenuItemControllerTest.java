package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.MenuItemUseCase;
import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuItemDTO;
import br.com.techchallenge.techbites.dtos.MenuItemEnableDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private MenuItemUseCase menuItemUseCase;

    @Mock
    private MenuItemDTOMapper menuItemMapper;

    @InjectMocks
    private MenuItemController menuItemController;

    private MenuItem menuItem;
    private MenuItemDTO menuItemDTO;
    private MenuItemCreateDTO menuItemCreateDTO;
    private MenuItemEnableDTO menuItemEnableDTO;

    @BeforeEach
    void setUp() {
        Menu menu = new Menu();
        menu.setId(1L);

        Type itemType = new Type();
        itemType.setId(1L);

        menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("Pizza");
        menuItem.setMenu(menu);
        menuItem.setItemType(itemType);

        menuItemDTO = new MenuItemDTO();
        menuItemDTO.setIdMenuItem(1L);
        menuItemDTO.setName("Pizza");

        menuItemCreateDTO = new MenuItemCreateDTO();
        menuItemEnableDTO = new MenuItemEnableDTO();
    }

    @Test
    @DisplayName("Deve criar um item de menu e retornar status 201 Created")
    void create() {
        when(menuItemUseCase.create(any(MenuItemCreateDTO.class))).thenReturn(menuItem);
        when(menuItemMapper.toResponseDTO(any(MenuItem.class))).thenReturn(menuItemDTO);

        ResponseEntity<MenuItemDTO> response = menuItemController.create(menuItemCreateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(menuItemDTO, response.getBody());
        verify(menuItemUseCase).create(menuItemCreateDTO);
        verify(menuItemMapper).toResponseDTO(menuItem);
    }

    @Test
    @DisplayName("Deve encontrar um item de menu pelo ID e retornar status 200 OK")
    void findById() {
        when(menuItemUseCase.findById(anyLong())).thenReturn(menuItem);
        when(menuItemMapper.toResponseDTO(any(MenuItem.class))).thenReturn(menuItemDTO);

        ResponseEntity<MenuItemDTO> response = menuItemController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menuItemDTO, response.getBody());
        verify(menuItemUseCase).findById(1L);
        verify(menuItemMapper).toResponseDTO(menuItem);
    }

    @Test
    @DisplayName("Deve encontrar itens de menu pelo ID do menu e retornar status 200 OK")
    void findByMenuId() {
        when(menuItemUseCase.findByMenuId(anyLong())).thenReturn(Collections.singletonList(menuItem));
        when(menuItemMapper.toResponseDTO(any(MenuItem.class))).thenReturn(menuItemDTO);

        ResponseEntity<List<MenuItemDTO>> response = menuItemController.findByMenuId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).isEmpty());
        assertEquals(menuItemDTO, response.getBody().get(0));
        verify(menuItemUseCase).findByMenuId(1L);
        verify(menuItemMapper).toResponseDTO(menuItem);
    }

    @Test
    @DisplayName("Deve deletar um item de menu e retornar status 204 No Content")
    void deleteTest() {
        doNothing().when(menuItemUseCase).delete(anyLong());

        ResponseEntity<Void> response = menuItemController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(menuItemUseCase).delete(1L);
    }

    @Test
    @DisplayName("Deve habilitar um item de menu e retornar status 204 No Content")
    void enableById() {
        doNothing().when(menuItemUseCase).enableById(anyLong(), any(MenuItemEnableDTO.class));

        ResponseEntity<Void> response = menuItemController.enableById(1L, menuItemEnableDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(menuItemUseCase).enableById(1L, menuItemEnableDTO);
    }
}