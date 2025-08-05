package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.MenuItemGateway;
import br.com.techchallenge.techbites.application.gateways.TypeGateway;
import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuItemEnableDTO; // Import adicionado
import br.com.techchallenge.techbites.infrastructure.gateways.TypeEntityMapper;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuItemUseCaseImp implements MenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final MenuUseCase menuUseCase;
    private final TypeGateway typeGateway;
    private final TypeEntityMapper typeMapper;

    public MenuItemUseCaseImp(MenuItemGateway menuItemGateway, MenuUseCase menuUseCase, TypeGateway typeGateway, TypeEntityMapper typeMapper) {
        this.menuItemGateway = menuItemGateway;
        this.menuUseCase = menuUseCase;
        this.typeGateway = typeGateway;
        this.typeMapper = typeMapper;
    }

    @Override
    public MenuItem create(MenuItemCreateDTO dto) {
        Menu menu = menuUseCase.findById(dto.getMenuId());
        if (menu.isInactive()) {
            throw new ResourceNotFoundException("Menu not found with id = " + dto.getMenuId());
        }

        Type itemType = typeGateway.findTypeById(dto.getItemTypeId())
                .map(typeMapper::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("ItemType not found with id = " + dto.getItemTypeId()));
        if (itemType.isInactive()) {
            throw new ResourceNotFoundException("ItemType not found with id = " + dto.getItemTypeId());
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setPicturePath(dto.getPicturePath());
        menuItem.setMenu(menu);
        menuItem.setItemType(itemType);
        menuItem.setCreatedAt(LocalDateTime.now());
        menuItem.setLastUpdatedAt(LocalDateTime.now());
        menuItem.setActive(true);

        return menuItemGateway.save(menuItem);
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id = " + id));
    }

    @Override
    public List<MenuItem> findByMenuId(Long menuId) {
        return menuItemGateway.findByMenuId(menuId);
    }

    @Override
    public void delete(Long id) {
        MenuItem menuItem = findById(id);
        if (menuItem.isInactive()) {
            return;
        }
        menuItemGateway.delete(menuItem);
    }

    @Override
    public void enableById(Long id, MenuItemEnableDTO dto) {
        MenuItem menuItem = findById(id);

        menuItem.setActive(true);
        menuItem.setPrice(dto.getPrice());
        menuItem.setLastUpdatedAt(LocalDateTime.now());

        menuItemGateway.save(menuItem);
    }
}