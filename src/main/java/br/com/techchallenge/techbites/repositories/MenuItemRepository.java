package br.com.techchallenge.techbites.repositories;

import br.com.techchallenge.techbites.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByMenuIdMenu(Long menuId);

}