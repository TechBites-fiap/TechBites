package br.com.techchallenge.techbites.mappers;

import br.com.techchallenge.techbites.dtos.RestaurantWithAddressRequestDTO;
import br.com.techchallenge.techbites.dtos.RestaurantWithAddressResponseDTO;
import br.com.techchallenge.techbites.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.entities.Restaurant;
import br.com.techchallenge.techbites.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class RestaurantMapper {

    private final AddressMapper addressMapper;
    private final UserMapper userMapper;

    public RestaurantMapper (AddressMapper addressMapper , UserMapper userMapper) {
        this.addressMapper = addressMapper;
        this.userMapper = userMapper;
    }

    public Restaurant toEntity(RestaurantWithAddressRequestDTO dto, User owner) {
        Restaurant entity = new Restaurant();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setCuisine(dto.cuisine());
        entity.setOpeningHour(dto.openingHour());

        entity.setCreatedAt(LocalDateTime.now());
        entity.setLastUpdatedAt(LocalDateTime.now());

        entity.setOwner(owner);

        entity.setAddress(addressMapper.toEntity(dto.address()));
        entity.getAddress().setCreatedAt(LocalDateTime.now());
        entity.getAddress().setLastUpdatedAt(LocalDateTime.now());

        return entity;
    }

    public RestaurantWithAddressResponseDTO toDTO(Restaurant entity) {
        return new RestaurantWithAddressResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCuisine(),
                entity.getOpeningHour(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getLastUpdatedAt(),
                userMapper.toDTO(entity.getOwner()),
                addressMapper.toDTO(entity.getAddress())
        );
    }

    public List<RestaurantWithAddressResponseDTO> toListDTO(List<Restaurant> restaurants) {
        if (Objects.isNull(restaurants)) {
            return Collections.emptyList();
        }
        return restaurants.stream()
                .map(this::toDTO)
                .toList();
    }
}
