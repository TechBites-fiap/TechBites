package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.MenuGateway;
import br.com.techchallenge.techbites.application.gateways.MenuItemGateway;
import br.com.techchallenge.techbites.application.gateways.RestaurantGateway;
import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuUpdateDTO;
import br.com.techchallenge.techbites.infrastructure.gateways.RestaurantEntityMapper;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuUseCaseImp implements MenuUseCase {

    private final MenuGateway menuGateway;
    private final RestaurantGateway restaurantGateway;
    private final RestaurantEntityMapper restaurantMapper;
    private final MenuItemGateway menuItemGateway;

    public MenuUseCaseImp(MenuGateway menuGateway, RestaurantGateway restaurantGateway, RestaurantEntityMapper restaurantMapper, MenuItemGateway menuItemGateway) {
        this.menuGateway = menuGateway;
        this.restaurantGateway = restaurantGateway;
        this.restaurantMapper = restaurantMapper;
        this.menuItemGateway = menuItemGateway;
    }

    @Override
    public Menu create(MenuCreateDTO dto) {
        Restaurant restaurant = restaurantGateway.findRestaurantById(dto.getRestaurantId())
                .map(restaurantMapper::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id = " + dto.getRestaurantId()));

        Menu menu = new Menu();
        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setRestaurant(restaurant);
        menu.setCreatedAt(LocalDateTime.now());
        menu.setLastUpdatedAt(LocalDateTime.now());
        menu.setActive(true);

        return menuGateway.save(menu);
    }

    @Override
    public Menu findById(Long id) {
        return menuGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id = " + id));
    }

    @Override
    public List<Menu> findByRestaurantId(Long restaurantId) {
        return menuGateway.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Menu> getAll(Boolean active) {
        return menuGateway.findAll(active);
    }

    @Override
    public Menu update(Long id, MenuUpdateDTO dto) {
        Menu menu = findById(id);
        if (menu.isInactive()) {
            throw new ResourceNotFoundException("Menu not found with id = " + id);
        }
        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setLastUpdatedAt(LocalDateTime.now());
        return menuGateway.save(menu);
    }

    @Override
    public void delete(Long id) {
        Menu menu = this.findById(id);

        if (menu.isInactive()) {
            return;
        }

        List<MenuItem> items = menuItemGateway.findByMenuId(id);
        for (MenuItem item : items) {
            menuItemGateway.delete(item);
        }

        // 2. Agora, desativa o menu em si
        menuGateway.delete(menu);
    }

    @Override
    public void enableById(Long id) {
        Menu menu = menuGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id = " + id));

        menu.setActive(true);
        menu.setLastUpdatedAt(LocalDateTime.now());
        menuGateway.save(menu);
    }
}