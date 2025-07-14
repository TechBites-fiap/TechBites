package br.com.techchallenge.techbites.mappers;

import br.com.techchallenge.techbites.dtos.AddressRequestDTO;
import br.com.techchallenge.techbites.dtos.AddressResponseDTO;
import br.com.techchallenge.techbites.entities.Address;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequestDTO dto) {
        Address entity = new Address();
        entity.setStreet(dto.street());
        entity.setNumber(dto.number());
        entity.setComplement(dto.complement());
        entity.setNeighborhood(dto.neighborhood());
        entity.setCity(dto.city());
        entity.setState(dto.state());
        entity.setZipCode(dto.zipCode());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setLastUpdatedAt(LocalDateTime.now());
        return entity;
    }

    public AddressResponseDTO toDTO(Address entity) {
        return new AddressResponseDTO(
                entity.getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getLastUpdatedAt()
        );
    }

    public List<AddressResponseDTO> toListDTO(List<Address> address) {
        if (Objects.isNull(address)) {
            return Collections.emptyList();
        }
        return address.stream()
                .map(this::toDTO)
                .toList();
    }

    public void updateEntity(Address addressExist, AddressRequestDTO addressDto) {
        addressExist.setStreet(addressDto.street());
        addressExist.setNumber(addressDto.number());
        addressExist.setComplement(addressDto.complement());
        addressExist.setNeighborhood(addressDto.neighborhood());
        addressExist.setCity(addressDto.city());
        addressExist.setState(addressDto.state());
        addressExist.setZipCode(addressDto.zipCode());
        addressExist.setLastUpdatedAt(LocalDateTime.now());
    }
}
