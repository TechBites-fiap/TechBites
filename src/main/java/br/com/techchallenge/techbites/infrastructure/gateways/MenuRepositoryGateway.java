package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.application.gateways.MenuGateway;
import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuRepository;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MenuRepositoryGateway implements MenuGateway {

    private final MenuRepository menuRepository;
    private final MenuEntityMapper menuMapper;

    public MenuRepositoryGateway(MenuRepository menuRepository, MenuEntityMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    @Transactional
    public Menu save(Menu menu) {
        MenuJpaEntity entity = menuMapper.toEntity(menu);
        return menuMapper.toDomain(menuRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Menu> findById(Long id) {
        Optional<MenuJpaEntity> entityOpt = menuRepository.findByIdInactive(id);
        entityOpt.ifPresent(entity -> Hibernate.initialize(entity.getItems()));
        return entityOpt.map(menuMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findByRestaurantId(Long restaurantId) {
        List<MenuJpaEntity> entities = menuRepository.findByRestaurantId(restaurantId);
        entities.forEach(entity -> Hibernate.initialize(entity.getItems()));
        return entities.stream()
                .map(menuMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAll(Boolean active) {
        List<MenuJpaEntity> entities;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        if (active == null) {
            entities = menuRepository.findAll(sort);
        } else {
            entities = menuRepository.findByActive(active, sort);
        }
        entities.forEach(entity -> Hibernate.initialize(entity.getItems()));
        return entities.stream()
                .map(menuMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Menu menu) {
        menuRepository.delete(menuMapper.toEntity(menu));
    }
}