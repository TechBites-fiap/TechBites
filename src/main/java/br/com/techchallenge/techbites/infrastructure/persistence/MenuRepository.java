package br.com.techchallenge.techbites.infrastructure.persistence;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuJpaEntity, Long> {
    List<MenuJpaEntity> findByRestaurantId(Long restaurantId);

    List<MenuJpaEntity> findByActive(Boolean active, Sort sort);

    @Query("SELECT m FROM MenuJpaEntity m WHERE m.id = :id")
    Optional<MenuJpaEntity> findByIdInactive(@Param("id") Long id);
}