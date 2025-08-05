package br.com.techchallenge.techbites.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemJpaEntity, Long> {
    List<MenuItemJpaEntity> findByMenuId(Long menuId);

    @Query("SELECT mi FROM MenuItemJpaEntity mi WHERE mi.id = :id")
    Optional<MenuItemJpaEntity> findByIdInactive(@Param("id") Long id);
}