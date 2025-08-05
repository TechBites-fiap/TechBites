package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.TypeGateway;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.infrastructure.gateways.TypeEntityMapper;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TypeUseCaseImp implements TypeUseCase {

    private final TypeGateway repository;
    private final TypeEntityMapper mapper;

    public TypeUseCaseImp(TypeGateway repository , TypeEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TypeJpaEntity createType(TypeJpaEntity entity) {
        entity.setActive(true);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setLastUpdatedAt(now);
        return repository.createType(entity);
    }

    @Override
    public Optional<TypeJpaEntity> findTypeById(Long id) throws ResourceNotFoundException {
        Optional<TypeJpaEntity> found = repository.findTypeById(id);
        Type domain = mapper.toDomain(found.get());
        if (domain.isInactive()) throw new ResourceNotFoundException("Type", "id", id.toString());
        return found;
    }

    @Override
    public List<TypeJpaEntity> getAllTypes(Boolean active) {
        return repository.getAllTypes(active);
    }

    @Override
    public void deleteTypeById(Long id) {
        repository.deleteTypeById(id);
    }

    @Override
    public void enableTypeById(Long id) {
        repository.enableTypeById(id);
    }

    @Override
    public TypeJpaEntity updateTypeById(Long id, TypeJpaEntity entity) {
        Optional<TypeJpaEntity> found = repository.findTypeById(id);
        Type domain = mapper.toDomain(found.get());

        if (domain.isInactive()) throw new ResourceNotFoundException("Type", "id", id.toString());

        domain.setType(entity.getType());
        domain.setLastUpdatedAt(LocalDateTime.now());

        TypeJpaEntity entityUpdate = mapper.toEntity(domain);

        return repository.updateType(entityUpdate);
    }
}
