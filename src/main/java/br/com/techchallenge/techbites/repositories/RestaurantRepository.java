package br.com.techchallenge.techbites.repositories;

import br.com.techchallenge.techbites.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository  extends JpaRepository<Restaurant, Long> {
}
