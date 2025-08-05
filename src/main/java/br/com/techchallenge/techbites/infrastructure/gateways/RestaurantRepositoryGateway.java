package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.application.gateways.RestaurantGateway;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantRepository;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantRepositoryGateway implements RestaurantGateway {

    private final RestaurantRepository repository;


    public RestaurantRepositoryGateway (RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public RestaurantJpaEntity createRestaurant(RestaurantJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<RestaurantJpaEntity> getAllRestaurants(Boolean active) {
        List<RestaurantJpaEntity> entities;
        if (active == null) {
            entities = repository.findAll(Sort.by(Sort.Direction.ASC,"id"));
        }else {
            entities = repository.findByActive(Sort.by(Sort.Direction.ASC,"id"), active);
        }
        return entities;
    }

    @Override
    public Optional<RestaurantJpaEntity> findRestaurantById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id.toString())));
    }

    @Override
    public void deleteRestaurantById(Long id) {
        RestaurantJpaEntity entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type" , "id" , id.toString())
        );
        repository.delete(entity);
    }

    @Override
    public void enableRestaurant(RestaurantJpaEntity entity) {
        repository.save(entity);
    }

    @Override
    public RestaurantJpaEntity updateAddress(RestaurantJpaEntity entity) {
        return repository.save(entity);
    }

}
