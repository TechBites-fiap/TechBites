package br.com.techchallenge.techbites.application.gateways;

import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;

import java.util.List;
import java.util.Optional;

public interface TypeGateway {
    TypeJpaEntity createType(TypeJpaEntity entity);

    Optional<TypeJpaEntity> findTypeById(Long id);

    List<TypeJpaEntity> getAllTypes(Boolean active);

    void deleteTypeById(Long id);

    void enableTypeById(Long id);

    TypeJpaEntity updateType(TypeJpaEntity entityUpdate);
}
