package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.MenuDTO;
import br.com.techchallenge.techbites.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuUpdateDTO;
import java.util.List;

public interface MenuService {
    MenuDTO findById(Long id);
    List<MenuDTO> findAll();
    List<MenuDTO> findAllByRestaurantId(Long restaurantId);
    MenuDTO create(MenuCreateDTO createDto);
    MenuDTO update(Long id, MenuUpdateDTO updateDto);
    void delete(Long id);
}