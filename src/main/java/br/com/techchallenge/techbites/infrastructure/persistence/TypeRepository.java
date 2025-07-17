package br.com.techchallenge.techbites.infrastructure.persistence;

import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<TypeJpaEntity, Long> {
    List<TypeJpaEntity> findByActive(Boolean active, Sort id);
}
