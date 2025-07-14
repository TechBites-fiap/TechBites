package br.com.techchallenge.techbites.repositories;

import br.com.techchallenge.techbites.entities.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository  extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByActive(Boolean active, Sort id);
}
