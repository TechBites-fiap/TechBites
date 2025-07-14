package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.MenuItemDTO;
import br.com.techchallenge.techbites.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.mappers.MenuItemMapper;
import br.com.techchallenge.techbites.entities.Menu;
import br.com.techchallenge.techbites.entities.MenuItem;
import br.com.techchallenge.techbites.repositories.MenuItemRepository;
import br.com.techchallenge.techbites.repositories.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuRepository menuRepository;
    private final MenuItemMapper menuItemMapper;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               MenuRepository menuRepository,
                               MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuRepository = menuRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public MenuItemDTO findById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MenuItem não encontrado com o id: " + id));
        return menuItemMapper.toDto(menuItem);
    }

    @Override
    public List<MenuItemDTO> findAllByMenuId(Long menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new EntityNotFoundException("Menu não encontrado com o id: " + menuId);
        }
        return menuItemRepository.findByMenuIdMenu(menuId).stream()
                .map(menuItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemDTO create(MenuItemCreateDTO dto) {
        MenuItem menuItem = menuItemMapper.toEntity(dto);

        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new EntityNotFoundException("Menu não encontrado com o id: " + dto.getMenuId()));

        menuItem.setMenu(menu);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        return menuItemMapper.toDto(savedMenuItem);
    }

    @Override
    public void delete(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new EntityNotFoundException("MenuItem não encontrado com o id: " + id);
        }
        menuItemRepository.deleteById(id);
    }
}