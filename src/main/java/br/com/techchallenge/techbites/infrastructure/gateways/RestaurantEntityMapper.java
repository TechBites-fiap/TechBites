package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEntityMapper {

    private final UserEntityMapper ownerMapper;
    private final AddressEntityMapper addressMapper;

    public RestaurantEntityMapper(UserEntityMapper ownerMapper, AddressEntityMapper addressMapper) {
        this.ownerMapper = ownerMapper;
        this.addressMapper = addressMapper;
    }

    public Restaurant toDomain(RestaurantJpaEntity entity) {
        if (entity == null) return null;

        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                addressMapper.toDomain(entity.getAddress()),
                entity.getCuisine(),
                entity.getOpeningHour(),
                ownerMapper.toDomain(entity.getOwner()),
                entity.getCreatedAt(),
                entity.getLastUpdatedAt(),
                entity.getActive()
        );
    }

    public RestaurantJpaEntity toEntity(Restaurant domain) {
        if (domain == null) return null;

        RestaurantJpaEntity entity = new RestaurantJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setCuisine(domain.getCuisine());
        entity.setOpeningHour(domain.getOpeningHour());
        entity.setActive(domain.getActive());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setLastUpdatedAt(domain.getLastUpdatedAt());

        entity.setAddress(addressMapper.toEntity(domain.getAddress()));
        entity.setOwner(ownerMapper.toEntity(domain.getOwner()));

        return entity;
    }
}
