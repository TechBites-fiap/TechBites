package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.dtos.TypeResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class TypeDTOMapper {

    public TypeResponseDTO toResponseDTO(TypeJpaEntity entity) {
        if (entity == null) return null;

        return new TypeResponseDTO(
                entity.getId(),
                entity.getType(),
                entity.getActive()
        );
    }

    public TypeJpaEntity toJpaEntity(@Valid TypeRequestDTO dto) {
        if (dto == null) return null;
        TypeJpaEntity entity = new TypeJpaEntity();
        entity.setType(dto.type());
        return entity;
    }

    public Optional<TypeResponseDTO> toOpResponseDTO(Optional<TypeJpaEntity> entity) {
        if (entity.isEmpty()) return Optional.empty();

        TypeResponseDTO responseDTO = new TypeResponseDTO(
            entity.get().getId(),
            entity.get().getType(),
            entity.get().getActive()
        );

        return Optional.of(responseDTO);
    }

    public List<TypeResponseDTO> toListResponseDTO(List<TypeJpaEntity> entities) {
        if (Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
