package br.com.techchallenge.techbites.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.*;

import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.infrastructure.gateways.AddressEntityMapper;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class AddressEntityMapperTest {

    private final AddressEntityMapper mapper = new AddressEntityMapper();

    @Test
    @DisplayName("GIVEN valid AddressJpaEntity WHEN toDomain THEN should map all fields correctly")
    void testToDomain_WithValidEntity() {
        // GIVEN
        AddressJpaEntity entity = new AddressJpaEntity();
        entity.setId(1L);
        entity.setStreet("Rua das Flores");
        entity.setNumber("123");
        entity.setComplement("Apto 101");
        entity.setNeighborhood("Centro");
        entity.setCity("São Paulo");
        entity.setState("SP");
        entity.setZipCode("01234-567");
        entity.setCreatedAt(LocalDateTime.now().minusDays(1));
        entity.setLastUpdatedAt(LocalDateTime.now());
        entity.setActive(true);

        // WHEN
        Address domain = mapper.toDomain(entity);

        // THEN
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getStreet(), domain.getStreet());
        assertEquals(entity.getNumber(), domain.getNumber());
        assertEquals(entity.getComplement(), domain.getComplement());
        assertEquals(entity.getNeighborhood(), domain.getNeighborhood());
        assertEquals(entity.getCity(), domain.getCity());
        assertEquals(entity.getState(), domain.getState());
        assertEquals(entity.getZipCode(), domain.getZipCode());
        assertEquals(entity.getCreatedAt(), domain.getCreatedAt());
        assertEquals(entity.getLastUpdatedAt(), domain.getLastUpdatedAt());
        assertEquals(entity.getActive(), domain.isActive());
    }

    @Test
    @DisplayName("GIVEN null AddressJpaEntity WHEN toDomain THEN should return null")
    void testToDomain_WithNullEntity() {
        // GIVEN
        AddressJpaEntity entity = null;

        // WHEN
        Address domain = mapper.toDomain(entity);

        // THEN
        assertNull(domain);
    }

    @Test
    @DisplayName("GIVEN valid Address domain WHEN toEntity THEN should map all fields correctly")
    void testToEntity_WithValidDomain() {
        // GIVEN
        Address domain = new Address(
                2L,
                "Av. Brasil",
                "456",
                "Casa",
                "Jardim",
                "Rio de Janeiro",
                "RJ",
                "12345-678",
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().minusHours(1),
                false
        );

        // WHEN
        AddressJpaEntity entity = mapper.toEntity(domain);

        // THEN
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getStreet(), entity.getStreet());
        assertEquals(domain.getNumber(), entity.getNumber());
        assertEquals(domain.getComplement(), entity.getComplement());
        assertEquals(domain.getNeighborhood(), entity.getNeighborhood());
        assertEquals(domain.getCity(), entity.getCity());
        assertEquals(domain.getState(), entity.getState());
        assertEquals(domain.getZipCode(), entity.getZipCode());
        assertEquals(domain.getCreatedAt(), entity.getCreatedAt());
        assertEquals(domain.getLastUpdatedAt(), entity.getLastUpdatedAt());
        assertEquals(domain.isActive(), entity.getActive());
    }

    @Test
    @DisplayName("GIVEN null Address domain WHEN toEntity THEN should return null")
    void testToEntity_WithNullDomain() {
        // GIVEN
        Address domain = null;

        // WHEN
        AddressJpaEntity entity = mapper.toEntity(domain);

        // THEN
        assertNull(entity);
    }
}
