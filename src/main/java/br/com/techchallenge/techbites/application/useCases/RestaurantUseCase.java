package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;

import java.util.List;
import java.util.Optional;

public interface RestaurantUseCase {
    public RestaurantJpaEntity createRestaurant(RestaurantJpaEntity entity);

    public List<RestaurantJpaEntity> getAllRestaurants(Boolean active);

    public Optional<RestaurantJpaEntity> findRestaurantById(Long id);

    public void deleteRestaurantById(Long id);

    public void enableRestaurantById(Long id);

    public RestaurantJpaEntity updateRestaurantById(Long id, RestaurantJpaEntity entity);
}
