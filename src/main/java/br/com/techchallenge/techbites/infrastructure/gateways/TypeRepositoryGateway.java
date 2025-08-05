package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.application.gateways.TypeGateway;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeRepository;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TypeRepositoryGateway implements TypeGateway {

    private final TypeRepository repository;
    private final TypeEntityMapper mapper;

    public TypeRepositoryGateway(TypeRepository typeRepository, TypeEntityMapper mapper) {
        this.repository = typeRepository;
        this.mapper = mapper;
    }

    @Override
    public TypeJpaEntity createType(TypeJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<TypeJpaEntity> findTypeById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Type", "id", id.toString())));
    }

    @Override
    public List<TypeJpaEntity> getAllTypes(Boolean active) {
        List<TypeJpaEntity> entities;

        if (active == null) {
             entities = repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }else {
            entities = repository.findByActive(Sort.by(Sort.Direction.ASC, "id"), active);
        }
        return entities;
    }

    @Override
    public void deleteTypeById(Long id) {
        TypeJpaEntity type = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type" , "id" , id.toString())
        );
        repository.delete(type);
    }

    @Override
    public void enableTypeById(Long id) {
        TypeJpaEntity type = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type" , "id" , id.toString())
        );
        type.setActive(true);
        repository.save(type);
    }

    @Override
    public TypeJpaEntity updateType(TypeJpaEntity entityUpdate) {
        return repository.save(entityUpdate);
    }


}
