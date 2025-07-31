package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.dtos.MenuItemDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MenuItemDTOMapperTest {

    @InjectMocks
    private MenuItemDTOMapper menuItemDTOMapper;

    @Test
    @DisplayName("Deve mapear MenuItem para MenuItemDTO corretamente")
    void toResponseDTO_Success() {
        Menu menu = new Menu();
        menu.setId(1L);

        Type itemType = new Type();
        itemType.setId(2L);

        MenuItem item = new MenuItem();
        item.setId(100L);
        item.setName("Item Teste");
        item.setDescription("Descrição do Item");
        item.setPrice(BigDecimal.TEN);
        item.setPicturePath("/path/to/img.png");
        item.setMenu(menu);
        item.setItemType(itemType);
        item.setCreatedAt(LocalDateTime.now());
        item.setLastUpdatedAt(LocalDateTime.now());

        MenuItemDTO dto = menuItemDTOMapper.toResponseDTO(item);

        assertNotNull(dto);
        assertEquals(item.getId(), dto.getIdMenuItem());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getDescription(), dto.getDescription());
        assertEquals(item.getPrice(), dto.getPrice());
        assertEquals(item.getPicturePath(), dto.getPicturePath());
        assertEquals(item.getMenu().getId(), dto.getMenuId());
        assertEquals(item.getItemType().getId(), dto.getItemTypeId());
        assertEquals(item.getCreatedAt(), dto.getCreatedAt());
        assertEquals(item.getLastUpdatedAt(), dto.getLastUpdatedAt());
    }

    @Test
    @DisplayName("Deve retornar nulo se o item de menu for nulo")
    void toResponseDTO_NullItem() {
        assertNull(menuItemDTOMapper.toResponseDTO(null));
    }
}