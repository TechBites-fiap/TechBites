package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TypeUseCase {

    public TypeJpaEntity createType(TypeJpaEntity type);

    public Optional<TypeJpaEntity> findTypeById(Long id) throws ResourceNotFoundException;

    List<TypeJpaEntity> getAllTypes(Boolean active);

    void deleteTypeById(Long id);

    void enableTypeById(Long id);

    TypeJpaEntity updateTypeById(Long id, TypeJpaEntity entity);
}
