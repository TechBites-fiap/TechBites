package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.dtos.UserUpdateRequestDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserDTOMapper {
    public Optional<UserResponseDTO> toOpResponseDTO(Optional<UserJpaEntity> entity) {

        if (entity.isEmpty()) return Optional.empty();

        return Optional.of(
                new UserResponseDTO(
                   entity.get().getId(),
                   entity.get().getName(),
                   entity.get().getEmail(),
                   entity.get().getRole(),
                   entity.get().getCreatedAt(),
                   entity.get().getCreatedAt(),
                   entity.get().getActive()
                )
        );
    }

    public UserJpaEntity toJpaEntity(UserRequestDTO userRequest) {
        if (userRequest == null) {
            return null;
        }

        UserJpaEntity entity = new UserJpaEntity();
        entity.setName(userRequest.name());
        entity.setEmail(userRequest.email());
        entity.setPassword(userRequest.password());
        entity.setRole(userRequest.role());
        entity.setActive(true);

        return entity;
    }

    public UserResponseDTO toResponseDTO(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new UserResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getLastUpdatedAt(),
                entity.getActive()
        );
    }

    public List<UserResponseDTO> toListResponseDTO(List<UserJpaEntity> entities) {
        if (Objects.isNull(entities)) return Collections.emptyList();
        return entities.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UserJpaEntity toJpaEntity(UserUpdateRequestDTO userRequest) {
        if (userRequest == null) {
            return null;
        }

        UserJpaEntity entity = new UserJpaEntity();
        entity.setName(userRequest.name());
        entity.setEmail(userRequest.email());
        entity.setRole(userRequest.role());

        return entity;
    }
}
