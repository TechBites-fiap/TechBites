package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.dtos.RestaurantWithAddressRequestDTO;

import br.com.techchallenge.techbites.dtos.RestaurantWithAddressResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class RestaurantDTOMapper {

    private final AddressDTOMapper addressMapper;
    private final UserDTOMapper userMapper;

    public RestaurantDTOMapper(AddressDTOMapper addressMapper, UserDTOMapper userMapper) {
        this.addressMapper = addressMapper;
        this.userMapper = userMapper;
    }

    public RestaurantJpaEntity toJpaEntity(RestaurantWithAddressRequestDTO dto) {

        RestaurantJpaEntity entity = new RestaurantJpaEntity();
        UserJpaEntity owner = new UserJpaEntity();
        owner.setId(dto.ownerId());

        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setCuisine(dto.cuisine());
        entity.setOpeningHour(dto.openingHour());

        entity.setAddress(addressMapper.toJpaEntity(dto.address()));
        entity.setOwner(owner);

        return entity;

    }

    public RestaurantWithAddressResponseDTO toResponseDTO(RestaurantJpaEntity created) {
        if (created == null) return null;

        return new RestaurantWithAddressResponseDTO(
                created.getId(),
                created.getName(),
                created.getDescription(),
                created.getCuisine(),
                created.getOpeningHour(),
                created.getActive(),
                created.getCreatedAt(),
                created.getLastUpdatedAt(),
                userMapper.toResponseDTO(created.getOwner()),
                addressMapper.toResponseDTO(created.getAddress())
        );
    }

    public List<RestaurantWithAddressResponseDTO> toListResponseDTO(List<RestaurantJpaEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<RestaurantWithAddressResponseDTO> toOpJpaEntity(Optional<RestaurantJpaEntity> entity) {
        return entity.map(this::toResponseDTO);
    }
}
