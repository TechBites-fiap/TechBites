package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuJpaEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MenuEntityMapper {

    private final RestaurantEntityMapper restaurantMapper;
    private final MenuItemEntityMapper menuItemMapper;

    public MenuEntityMapper(RestaurantEntityMapper restaurantMapper, MenuItemEntityMapper menuItemMapper) {
        this.restaurantMapper = restaurantMapper;
        this.menuItemMapper = menuItemMapper;
    }

    public Menu toDomain(MenuJpaEntity entity) {
        if (entity == null) return null;
        Menu domain = new Menu();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setRestaurant(restaurantMapper.toDomain(entity.getRestaurant()));
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setLastUpdatedAt(entity.getLastUpdatedAt());
        domain.setActive(entity.getActive());

        if (entity.getItems() != null) {
            domain.setItems(entity.getItems().stream()
                    .map(menuItemMapper::toDomain)
                    .collect(Collectors.toList()));
        }

        return domain;
    }

    public MenuJpaEntity toEntity(Menu domain) {
        if (domain == null) return null;
        MenuJpaEntity entity = new MenuJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setRestaurant(restaurantMapper.toEntity(domain.getRestaurant()));
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setLastUpdatedAt(domain.getLastUpdatedAt());
        entity.setActive(domain.getActive());

        if (domain.getItems() != null) {
            entity.setItems(domain.getItems().stream()
                    .map(menuItemMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        return entity;
    }
}