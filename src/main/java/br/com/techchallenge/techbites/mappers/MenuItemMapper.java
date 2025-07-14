package br.com.techchallenge.techbites.mappers;

import br.com.techchallenge.techbites.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuItemDTO;
import br.com.techchallenge.techbites.entities.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public MenuItemDTO toDto(MenuItem entity) {
        if (entity == null) {
            return null;
        }

        MenuItemDTO dto = new MenuItemDTO();
        dto.setIdMenuItem(entity.getIdMenuItem());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setPicturePath(entity.getPicturePath());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastUpdatedAt(entity.getLastUpdatedAt());

        if (entity.getMenu() != null) {
            dto.setMenuId(entity.getMenu().getIdMenu());
        }

        return dto;
    }

    public MenuItem toEntity(MenuItemCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        MenuItem entity = new MenuItem();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setPicturePath(dto.getPicturePath());

        return entity;
    }
}