package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressEntityMapper {
    public Address toDomain(AddressJpaEntity addressJpaEntity) {
        if (addressJpaEntity == null) return null;

        return new Address(
                addressJpaEntity.getId(),
                addressJpaEntity.getStreet(),
                addressJpaEntity.getNumber(),
                addressJpaEntity.getComplement(),
                addressJpaEntity.getNeighborhood(),
                addressJpaEntity.getCity(),
                addressJpaEntity.getState(),
                addressJpaEntity.getZipCode(),
                addressJpaEntity.getCreatedAt(),
                addressJpaEntity.getLastUpdatedAt(),
                addressJpaEntity.getActive()
        );
    }

    public AddressJpaEntity toEntity(Address domain) {
        if (domain == null) return null;

        AddressJpaEntity entity = new AddressJpaEntity();
        entity.setId(domain.getId());
        entity.setStreet(domain.getStreet());
        entity.setNumber(domain.getNumber());
        entity.setComplement(domain.getComplement());
        entity.setNeighborhood(domain.getNeighborhood());
        entity.setCity(domain.getCity());
        entity.setState(domain.getState());
        entity.setZipCode(domain.getZipCode());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setLastUpdatedAt(domain.getLastUpdatedAt());
        entity.setActive(domain.isActive());

        return entity;
    }
}
