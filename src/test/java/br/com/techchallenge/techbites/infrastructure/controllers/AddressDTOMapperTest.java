package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.infrastructure.dtos.AddressRequestDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.AddressResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddressDTOMapperTest {

    private final AddressDTOMapper mapper = new AddressDTOMapper();

    @Test
    @DisplayName("GIVEN AddressRequestDTO WHEN toJpaEntity THEN retorna AddressJpaEntity corretamente")
    void shouldConvertRequestDTOToJpaEntity() {
        // GIVEN
        AddressRequestDTO dto = new AddressRequestDTO(
                "Rua da Quebrada",
                "123",
                "Fundos",
                "Jardim das Lendas",
                "Sampa",
                "SP",
                "01234-567"
        );

        // WHEN
        AddressJpaEntity entity = mapper.toJpaEntity(dto);

        // THEN
        assertThat(entity.getStreet()).isEqualTo("Rua da Quebrada");
        assertThat(entity.getNumber()).isEqualTo("123");
        assertThat(entity.getComplement()).isEqualTo("Fundos");
        assertThat(entity.getNeighborhood()).isEqualTo("Jardim das Lendas");
        assertThat(entity.getCity()).isEqualTo("Sampa");
        assertThat(entity.getState()).isEqualTo("SP");
        assertThat(entity.getZipCode()).isEqualTo("01234-567");
    }

    @Test
    @DisplayName("GIVEN AddressJpaEntity WHEN toResponseDTO THEN retorna AddressResponseDTO corretamente")
    void shouldConvertJpaEntityToResponseDTO() {
        // GIVEN
        AddressJpaEntity entity = new AddressJpaEntity();
        entity.setId(1L);
        entity.setStreet("Rua dos Devs");
        entity.setNumber("999");
        entity.setComplement("Casa 2");
        entity.setNeighborhood("Vila Code");
        entity.setCity("SP");
        entity.setState("SP");
        entity.setZipCode("00000-000");
        entity.setActive(true);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setLastUpdatedAt(LocalDateTime.now());

        // WHEN
        AddressResponseDTO dto = mapper.toResponseDTO(entity);

        // THEN
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.street()).isEqualTo("Rua dos Devs");
        assertThat(dto.number()).isEqualTo("999");
        assertThat(dto.complement()).isEqualTo("Casa 2");
        assertThat(dto.neighborhood()).isEqualTo("Vila Code");
        assertThat(dto.city()).isEqualTo("SP");
        assertThat(dto.state()).isEqualTo("SP");
        assertThat(dto.zipCode()).isEqualTo("00000-000");
        assertThat(dto.active()).isTrue();
        assertThat(dto.createdAt()).isNotNull();
        assertThat(dto.lastUpdatedAt()).isNotNull();
    }

    // ===== Testes que faltavam =====

    @Test
    @DisplayName("GIVEN AddressRequestDTO WHEN toDomain THEN retorna Domain Address corretamente")
    void shouldConvertRequestDTOToDomain() {
        // GIVEN
        AddressRequestDTO dto = new AddressRequestDTO(
                "Rua da Quebrada",
                "123",
                "Fundos",
                "Jardim das Lendas",
                "Sampa",
                "SP",
                "01234-567"
        );

        // WHEN
        Address domain = mapper.toDomain(dto);

        // THEN
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isNull();
        assertThat(domain.getStreet()).isEqualTo("Rua da Quebrada");
        assertThat(domain.getNumber()).isEqualTo("123");
        assertThat(domain.getComplement()).isEqualTo("Fundos");
        assertThat(domain.getNeighborhood()).isEqualTo("Jardim das Lendas");
        assertThat(domain.getCity()).isEqualTo("Sampa");
        assertThat(domain.getState()).isEqualTo("SP");
        assertThat(domain.getZipCode()).isEqualTo("01234-567");
        assertThat(domain.isActive()).isTrue();
        assertThat(domain.getCreatedAt()).isNull();
        assertThat(domain.getLastUpdatedAt()).isNull();
    }

    @Test
    @DisplayName("GIVEN Domain Address WHEN toResponseDTO THEN retorna AddressResponseDTO corretamente")
    void shouldConvertDomainToResponseDTO() {
        // GIVEN
        Address domain = new Address(
                1L,
                "Rua dos Devs",
                "999",
                "Casa 2",
                "Vila Code",
                "SP",
                "SP",
                "00000-000",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
        );

        // WHEN
        AddressResponseDTO dto = mapper.toResponseDTO(domain);

        // THEN
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.street()).isEqualTo("Rua dos Devs");
        assertThat(dto.number()).isEqualTo("999");
        assertThat(dto.complement()).isEqualTo("Casa 2");
        assertThat(dto.neighborhood()).isEqualTo("Vila Code");
        assertThat(dto.city()).isEqualTo("SP");
        assertThat(dto.state()).isEqualTo("SP");
        assertThat(dto.zipCode()).isEqualTo("00000-000");
        assertThat(dto.active()).isTrue();
        assertThat(dto.createdAt()).isNotNull();
        assertThat(dto.lastUpdatedAt()).isNotNull();
    }

}

