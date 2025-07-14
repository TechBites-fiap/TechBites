package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuItemDTO;

import java.util.List;

public interface MenuItemService {
    MenuItemDTO findById(Long id);
    List<MenuItemDTO> findAllByMenuId(Long menuId);
    MenuItemDTO create(MenuItemCreateDTO menuItemCreateDTO);
    void delete(Long id);

}
