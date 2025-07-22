package br.com.techchallenge.techbites.application.gateways;

import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {
    public RestaurantJpaEntity createRestaurant(RestaurantJpaEntity entity);

    public List<RestaurantJpaEntity> getAllRestaurants(Boolean active);

    public Optional<RestaurantJpaEntity> findRestaurantById(Long id);

    public void deleteRestaurantById(Long id);

    public void enableRestaurant(RestaurantJpaEntity entity);

    public RestaurantJpaEntity updateAddress(RestaurantJpaEntity toUpdate);
}
