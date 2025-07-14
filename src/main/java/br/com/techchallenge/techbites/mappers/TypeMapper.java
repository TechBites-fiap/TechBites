package br.com.techchallenge.techbites.mappers;

import br.com.techchallenge.techbites.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.dtos.TypeResponseDTO;
import br.com.techchallenge.techbites.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.entities.Type;
import br.com.techchallenge.techbites.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class TypeMapper {

    public Type toEntity(TypeRequestDTO dto) {
        Type entity = new Type();
        entity.setType(dto.type());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setLastUpdatedAt(LocalDateTime.now());
        return entity;
    }

    public List<TypeResponseDTO> toListDTO(List<Type> types) {
        if (Objects.isNull(types)) {
            return Collections.emptyList();
        }
        return types.stream()
                .map(this::toDTO)
                .toList();
    }

    public TypeResponseDTO toDTO(Type entity) {
        return new TypeResponseDTO(
                entity.getId(),
                entity.getType(),
                entity.getActive()
        );
    }

    public void updateEntity(Type existingType, TypeRequestDTO newData) {
        existingType.setType(newData.type());
        existingType.setLastUpdatedAt(LocalDateTime.now());
    }

}
