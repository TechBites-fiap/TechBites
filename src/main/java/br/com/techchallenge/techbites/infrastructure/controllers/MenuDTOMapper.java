package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.dtos.MenuDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MenuDTOMapper {

    private final MenuItemDTOMapper menuItemMapper;

    public MenuDTOMapper(MenuItemDTOMapper menuItemMapper) {
        this.menuItemMapper = menuItemMapper;
    }

    public MenuDTO toResponseDTO(Menu menu) {
        if (menu == null) return null;
        MenuDTO dto = new MenuDTO();
        dto.setIdMenu(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setRestaurantId(menu.getRestaurant().getId());
        dto.setCreatedAt(menu.getCreatedAt());
        dto.setLastUpdatedAt(menu.getLastUpdatedAt());
        if (menu.getItems() != null) {
            dto.setItems(menu.getItems().stream().map(menuItemMapper::toResponseDTO).collect(Collectors.toList()));
        }
        return dto;
    }
}