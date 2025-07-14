package br.com.techchallenge.techbites.repositories;

import br.com.techchallenge.techbites.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByRestaurantId(Long restaurantId);
    Optional<Menu> findByNameAndRestaurantId(String name, Long restaurantId);
}