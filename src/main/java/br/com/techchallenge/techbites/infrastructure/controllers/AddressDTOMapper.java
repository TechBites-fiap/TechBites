package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.dtos.AddressRequestDTO;
import br.com.techchallenge.techbites.dtos.AddressResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressDTOMapper {

    public AddressJpaEntity toJpaEntity(AddressRequestDTO dto) {
        AddressJpaEntity entity = new AddressJpaEntity();
        entity.setStreet(dto.street());
        entity.setNumber(dto.number());
        entity.setComplement(dto.complement());
        entity.setNeighborhood(dto.neighborhood());
        entity.setCity(dto.city());
        entity.setState(dto.state());
        entity.setZipCode(dto.zipCode());
        return entity;
    }

    public AddressResponseDTO toResponseDTO(AddressJpaEntity addressJpaEntity) {
        return new AddressResponseDTO(
                addressJpaEntity.getId(),
                addressJpaEntity.getStreet(),
                addressJpaEntity.getNumber(),
                addressJpaEntity.getComplement(),
                addressJpaEntity.getNeighborhood(),
                addressJpaEntity.getCity(),
                addressJpaEntity.getState(),
                addressJpaEntity.getZipCode(),
                addressJpaEntity.getActive(),
                addressJpaEntity.getCreatedAt(),
                addressJpaEntity.getLastUpdatedAt()
        );
    }
}
