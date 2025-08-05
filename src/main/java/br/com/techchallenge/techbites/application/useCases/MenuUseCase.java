package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuUpdateDTO;

import java.util.List;

public interface MenuUseCase {
    Menu create(MenuCreateDTO dto);
    Menu findById(Long id);
    List<Menu> findByRestaurantId(Long restaurantId);
    List<Menu> getAll(Boolean active);
    Menu update(Long id, MenuUpdateDTO dto);
    void delete(Long id);
    void enableById(Long id);
}