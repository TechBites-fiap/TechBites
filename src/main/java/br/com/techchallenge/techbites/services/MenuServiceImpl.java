package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.MenuDTO;
import br.com.techchallenge.techbites.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuUpdateDTO;
import br.com.techchallenge.techbites.mappers.MenuMapper;
import br.com.techchallenge.techbites.entities.Menu;
import br.com.techchallenge.techbites.entities.Restaurant;
import br.com.techchallenge.techbites.repositories.MenuRepository;
import br.com.techchallenge.techbites.repositories.RestaurantRepository;
import br.com.techchallenge.techbites.services.exceptions.DuplicateKeyException;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, RestaurantRepository restaurantRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDTO findById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu não encontrado com o id: " + id));
        return menuMapper.toDto(menu);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> findAll() {
        return menuRepository.findAll().stream()
                .map(menuMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> findAllByRestaurantId(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurante não encontrado com o id: " + restaurantId);
        }
        return menuRepository.findByRestaurantId(restaurantId).stream()
                .map(menuMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MenuDTO create(MenuCreateDTO createDto) {
        menuRepository.findByNameAndRestaurantId(createDto.getName(), createDto.getRestaurantId())
                .ifPresent(existingMenu -> {
                    throw new DuplicateKeyException("Já existe um menu com o nome '" + createDto.getName() + "' para este restaurante.");
                });

        Restaurant restaurant = restaurantRepository.findById(createDto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com o id: " + createDto.getRestaurantId()));

        Menu menu = menuMapper.toEntity(createDto);
        menu.setRestaurant(restaurant);
        Menu savedMenu = menuRepository.save(menu);

        return menuMapper.toDto(savedMenu);
    }

    @Override
    @Transactional
    public MenuDTO update(Long id, MenuUpdateDTO updateDto) {
        Menu menuToUpdate = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu não encontrado com o id: " + id));

        if (!menuToUpdate.getName().equalsIgnoreCase(updateDto.getName())) {
            menuRepository.findByNameAndRestaurantId(updateDto.getName(), menuToUpdate.getRestaurant().getId())
                    .ifPresent(existingMenu -> {
                        throw new DuplicateKeyException("Já existe um menu com o nome '" + updateDto.getName() + "' para este restaurante.");
                    });
        }

        menuMapper.updateEntityFromDto(updateDto, menuToUpdate);
        Menu updatedMenu = menuRepository.save(menuToUpdate);

        return menuMapper.toDto(updatedMenu);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu não encontrado com o id: " + id));

        menuRepository.delete(menu);
    }
}