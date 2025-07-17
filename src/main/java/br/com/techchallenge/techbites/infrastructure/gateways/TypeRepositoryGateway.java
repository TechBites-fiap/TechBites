package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.application.gateways.TypeGateway;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeRepository;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public class TypeRepositoryGateway implements TypeGateway {

    private final TypeRepository repository;
    private final TypeEntityMapper mapper;

    public TypeRepositoryGateway(
            TypeRepository typeRepository,
            TypeEntityMapper mapper
    ) {
        this.repository = typeRepository;
        this.mapper = mapper;
    }

    @Override
    public Type createType(Type type) {
        TypeJpaEntity entity = mapper.toEntity(type);
        TypeJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }


    @Override
    public List<Type> findAllType(Boolean active) {
        List<TypeJpaEntity> entities;
        if (active == null) {
            entities = repository.findAll();
        }else {
            entities = repository.findByActive(true, Sort.by(Sort.Direction.ASC, "id"));
        }
        return mapper.toDomainList(entities);
    }

    @Override
    public Optional<Type> findTypeById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Type" , "id" , id.toString() )
                ))
                .map(this.mapper::toDomain);
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
    public Type updateTypeById(Long id, Type newTypeData) {
        TypeJpaEntity typeExist = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Type" , "id" , id.toString())
        );

        typeExist.setType(newTypeData.getType());
        typeExist.setLastUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(repository.save(typeExist));
    }
}
