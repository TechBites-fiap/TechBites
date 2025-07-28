package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuItemJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuJpaEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MenuItemEntityMapper {

    private final MenuEntityMapper menuMapper;
    private final TypeEntityMapper typeMapper;
    private final RestaurantEntityMapper restaurantMapper;

    public MenuItemEntityMapper(@Lazy MenuEntityMapper menuMapper, TypeEntityMapper typeMapper, RestaurantEntityMapper restaurantMapper) {
        this.menuMapper = menuMapper;
        this.typeMapper = typeMapper;
        this.restaurantMapper = restaurantMapper;
    }

    public MenuItem toDomain(MenuItemJpaEntity entity) {
        if (entity == null) return null;
        MenuItem domain = new MenuItem();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setPrice(entity.getPrice());
        domain.setPicturePath(entity.getPicturePath());
        domain.setMenu(this.toSimpleMenuDomain(entity.getMenu()));
        domain.setItemType(typeMapper.toDomain(entity.getItemType()));
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setLastUpdatedAt(entity.getLastUpdatedAt());
        domain.setActive(entity.getActive());
        return domain;
    }

    public MenuItemJpaEntity toEntity(MenuItem domain) {
        if (domain == null) return null;
        MenuItemJpaEntity entity = new MenuItemJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setPicturePath(domain.getPicturePath());
        entity.setMenu(menuMapper.toEntity(domain.getMenu()));
        entity.setItemType(typeMapper.toEntity(domain.getItemType()));
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setLastUpdatedAt(domain.getLastUpdatedAt());
        entity.setActive(domain.getActive());
        return entity;
    }

    private Menu toSimpleMenuDomain(MenuJpaEntity entity) {
        if (entity == null) return null;
        Menu domain = new Menu();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setRestaurant(restaurantMapper.toDomain(entity.getRestaurant()));
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setLastUpdatedAt(entity.getLastUpdatedAt());
        domain.setActive(entity.getActive());
        domain.setItems(null);
        return domain;
    }
}