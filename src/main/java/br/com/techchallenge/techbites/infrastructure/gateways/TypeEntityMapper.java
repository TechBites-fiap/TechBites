package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import br.com.techchallenge.techbites.domain.models.Type;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TypeEntityMapper {

    public Type toDomain(TypeJpaEntity entity) {
        if (entity == null) return null;

        Type type = new Type();
        type.setId(entity.getId());
        type.setType(entity.getType());
        type.setActive(entity.getActive());
        type.setCreatedAt(entity.getCreatedAt());
        type.setLastUpdatedAt(entity.getLastUpdatedAt());

        return type;
    }

    public TypeJpaEntity toEntity(Type domain) {
        if (domain == null) return null;

        TypeJpaEntity entity = new TypeJpaEntity();
        entity.setId(domain.getId());
        entity.setType(domain.getType());
        entity.setActive(domain.getActive());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setLastUpdatedAt(domain.getLastUpdatedAt());

        return entity;
    }

    public List<Type> toDomainList(List<TypeJpaEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public List<TypeJpaEntity> toEntityList(List<Type> domainList) {
        return domainList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }



}
