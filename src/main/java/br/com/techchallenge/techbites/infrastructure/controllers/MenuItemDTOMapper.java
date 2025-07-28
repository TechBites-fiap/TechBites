package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.dtos.MenuItemDTO;
import org.springframework.stereotype.Component;

@Component
public class MenuItemDTOMapper {

    public MenuItemDTO toResponseDTO(MenuItem item) {
        if (item == null) return null;
        MenuItemDTO dto = new MenuItemDTO();
        dto.setIdMenuItem(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setPrice(item.getPrice());
        dto.setPicturePath(item.getPicturePath());
        dto.setMenuId(item.getMenu().getId());
        dto.setItemTypeId(item.getItemType().getId());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setLastUpdatedAt(item.getLastUpdatedAt());
        return dto;
    }
}