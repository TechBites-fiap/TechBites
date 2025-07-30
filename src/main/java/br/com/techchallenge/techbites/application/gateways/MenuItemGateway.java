package br.com.techchallenge.techbites.application.gateways;

import br.com.techchallenge.techbites.domain.models.MenuItem;
import java.util.List;
import java.util.Optional;

public interface MenuItemGateway {
    MenuItem save(MenuItem menuItem);
    Optional<MenuItem> findById(Long id);
    List<MenuItem> findByMenuId(Long menuId);
    void delete(MenuItem menuItem);
}