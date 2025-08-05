package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuItemEnableDTO; // Import adicionado

import java.util.List;

public interface MenuItemUseCase {
    MenuItem create(MenuItemCreateDTO dto);
    MenuItem findById(Long id);
    List<MenuItem> findByMenuId(Long menuId);
    void delete(Long id);
    void enableById(Long id, MenuItemEnableDTO dto);
}