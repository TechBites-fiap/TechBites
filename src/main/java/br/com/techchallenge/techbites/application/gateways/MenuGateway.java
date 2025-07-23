package br.com.techchallenge.techbites.application.gateways;

import br.com.techchallenge.techbites.domain.models.Menu;
import java.util.List;
import java.util.Optional;

public interface MenuGateway {
    Menu save(Menu menu);
    Optional<Menu> findById(Long id);
    List<Menu> findByRestaurantId(Long restaurantId);
    List<Menu> findAll(Boolean active);
    void delete(Menu menu);
}