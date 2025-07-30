package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.application.gateways.MenuItemGateway;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuItemJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MenuItemRepositoryGateway implements MenuItemGateway {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemEntityMapper menuItemMapper;

    public MenuItemRepositoryGateway(MenuItemRepository menuItemRepository, MenuItemEntityMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        MenuItemJpaEntity entity = menuItemMapper.toEntity(menuItem);
        return menuItemMapper.toDomain(menuItemRepository.save(entity));
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return menuItemRepository.findByIdInactive(id).map(menuItemMapper::toDomain);
    }

    @Override
    public List<MenuItem> findByMenuId(Long menuId) {
        return menuItemRepository.findByMenuId(menuId).stream()
                .map(menuItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(MenuItem menuItem) {
        menuItemRepository.delete(menuItemMapper.toEntity(menuItem));
    }
}