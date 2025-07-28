package br.com.techchallenge.techbites.infrastructure.persistence;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantJpaEntity, Long> {

    List<RestaurantJpaEntity> findByActive(Sort id, Boolean active);
}
