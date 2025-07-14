package br.com.techchallenge.techbites.mappers;

import br.com.techchallenge.techbites.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuDTO;
import br.com.techchallenge.techbites.dtos.MenuUpdateDTO;
import br.com.techchallenge.techbites.entities.Menu;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MenuMapper {

    private final MenuItemMapper menuItemMapper;

    public MenuMapper(MenuItemMapper menuItemMapper) {
        this.menuItemMapper = menuItemMapper;
    }

    public MenuDTO toDto(Menu entity) {
        if (entity == null) {
            return null;
        }

        MenuDTO dto = new MenuDTO();
        dto.setIdMenu(entity.getIdMenu());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastUpdatedAt(entity.getLastUpdatedAt());

        if (entity.getRestaurant() != null) {
            dto.setRestaurantId(entity.getRestaurant().getId());
        }

        if (entity.getMenuItems() != null) {
            dto.setItems(
                    entity.getMenuItems().stream()
                            .map(menuItemMapper::toDto) // Equivalente a .map(item -> menuItemMapper.toDto(item))
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Menu toEntity(MenuCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Menu entity = new Menu();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public void updateEntityFromDto(MenuUpdateDTO dto, Menu entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }
}