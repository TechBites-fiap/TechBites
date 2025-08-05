package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.infrastructure.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.TypeResponseDTO;

import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TypeDTOMapperTest {

    private final TypeDTOMapper mapper = new TypeDTOMapper();

    @Test
    @DisplayName("GIVEN valid entity WHEN toResponseDTO THEN returns matching DTO")
    void testToResponseDTO() {
        TypeJpaEntity entity = new TypeJpaEntity();
        entity.setId(1L);
        entity.setType("Pizza");
        entity.setActive(true);

        TypeResponseDTO dto = mapper.toResponseDTO(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.type()).isEqualTo("Pizza");
        assertThat(dto.active()).isTrue();
    }

    @Test
    @DisplayName("GIVEN null entity WHEN toResponseDTO THEN returns null")
    void testToResponseDTONull() {
        assertThat(mapper.toResponseDTO(null)).isNull();
    }

    @Test
    @DisplayName("GIVEN valid DTO WHEN toJpaEntity THEN returns matching entity")
    void testToJpaEntity() {
        TypeRequestDTO dto = new TypeRequestDTO("Pizza");

        TypeJpaEntity entity = mapper.toJpaEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getType()).isEqualTo("Pizza");
    }

    @Test
    @DisplayName("GIVEN null DTO WHEN toJpaEntity THEN returns null")
    void testToJpaEntityNull() {
        assertThat(mapper.toJpaEntity(null)).isNull();
    }

    @Test
    @DisplayName("GIVEN valid Optional entity WHEN toOpResponseDTO THEN returns Optional DTO")
    void testToOpResponseDTO() {
        TypeJpaEntity entity = new TypeJpaEntity();
        entity.setId(1L);
        entity.setType("Pizza");
        entity.setActive(true);

        Optional<TypeResponseDTO> result = mapper.toOpResponseDTO(Optional.of(entity));

        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(1L);
        assertThat(result.get().type()).isEqualTo("Pizza");
        assertThat(result.get().active()).isTrue();
    }

    @Test
    @DisplayName("GIVEN empty Optional WHEN toOpResponseDTO THEN returns empty Optional")
    void testToOpResponseDTOEmpty() {
        assertThat(mapper.toOpResponseDTO(Optional.empty())).isEmpty();
    }

    @Test
    @DisplayName("GIVEN list of entities WHEN toListResponseDTO THEN returns list of DTOs")
    void testToListResponseDTO() {
        TypeJpaEntity e1 = new TypeJpaEntity();
        e1.setId(1L);
        e1.setType("Pizza");
        e1.setActive(true);

        TypeJpaEntity e2 = new TypeJpaEntity();
        e2.setId(2L);
        e2.setType("Lanche");
        e2.setActive(false);

        List<TypeResponseDTO> result = mapper.toListResponseDTO(List.of(e1, e2));

        assertThat(result).hasSize(2);
        assertThat(result.get(0).type()).isEqualTo("Pizza");
        assertThat(result.get(1).type()).isEqualTo("Lanche");
    }

    @Test
    @DisplayName("GIVEN null list WHEN toListResponseDTO THEN returns empty list")
    void testToListResponseDTONull() {
        assertThat(mapper.toListResponseDTO(null)).isEmpty();
    }

    @Test
    @DisplayName("GIVEN empty list WHEN toListResponseDTO THEN returns empty list")
    void testToListResponseDTOEmpty() {
        assertThat(mapper.toListResponseDTO(Collections.emptyList())).isEmpty();
    }
}

