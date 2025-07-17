package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.dtos.TypeResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TypeDTOMapper {


    // Request DTO → Domínio
    public Type toDomain(TypeRequestDTO dto) {
        if (dto == null) return null;

        Type type = new Type();
        type.setType(dto.type());
        // NÃO seta id, active, createdAt etc porque isso é trabalho do UseCase
        return type;
    }

    // Domínio → Response DTO
    public TypeResponseDTO toDTO(Type domain) {
        if (domain == null) return null;

        return new TypeResponseDTO(
                domain.getId(),
                domain.getType(),
                domain.getActive()
        );
    }

    // Lista de domínio → Lista de Response DTO
    public List<TypeResponseDTO> toListDTO(List<Type> domainList) {
        if (domainList == null) return null;

        return domainList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
