package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TypeEntityMapperTest {

    private TypeEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TypeEntityMapper();
    }

    @Test
    @DisplayName("GIVEN null TypeJpaEntity WHEN toDomain THEN should return null")
    void toDomain_NullEntity_ReturnsNull() {
        // GIVEN
        TypeJpaEntity entity = null;

        // WHEN
        Type domain = mapper.toDomain(entity);

        // THEN
        assertNull(domain);
    }

    @Test
    @DisplayName("GIVEN valid TypeJpaEntity WHEN toDomain THEN should map correctly")
    void toDomain_ValidEntity_ReturnsDomain() {
        // GIVEN
        TypeJpaEntity entity = new TypeJpaEntity();
        entity.setId(1L);
        entity.setType("Entrada");
        entity.setActive(true);
        entity.setCreatedAt(LocalDateTime.now().minusDays(1));
        entity.setLastUpdatedAt(LocalDateTime.now());

        // WHEN
        Type domain = mapper.toDomain(entity);

        // THEN
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getType(), domain.getType());
        assertEquals(entity.getActive(), domain.getActive());
        assertEquals(entity.getCreatedAt(), domain.getCreatedAt());
        assertEquals(entity.getLastUpdatedAt(), domain.getLastUpdatedAt());
    }

    @Test
    @DisplayName("GIVEN null Type domain WHEN toEntity THEN should return null")
    void toEntity_NullDomain_ReturnsNull() {
        // GIVEN
        Type domain = null;

        // WHEN
        TypeJpaEntity entity = mapper.toEntity(domain);

        // THEN
        assertNull(entity);
    }

    @Test
    @DisplayName("GIVEN valid Type domain WHEN toEntity THEN should map correctly")
    void toEntity_ValidDomain_ReturnsEntity() {
        // GIVEN
        Type domain = new Type();
        domain.setId(2L);
        domain.setType("Saida");
        domain.setActive(false);
        domain.setCreatedAt(LocalDateTime.now().minusDays(2));
        domain.setLastUpdatedAt(LocalDateTime.now().minusHours(5));

        // WHEN
        TypeJpaEntity entity = mapper.toEntity(domain);

        // THEN
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getType(), entity.getType());
        assertEquals(domain.getActive(), entity.getActive());
        assertEquals(domain.getCreatedAt(), entity.getCreatedAt());
        assertEquals(domain.getLastUpdatedAt(), entity.getLastUpdatedAt());
    }

    @Test
    @DisplayName("GIVEN list of TypeJpaEntity WHEN toDomainList THEN should map all")
    void toDomainList_ValidEntities_ReturnsDomainList() {
        // GIVEN
        TypeJpaEntity entity1 = new TypeJpaEntity();
        entity1.setId(1L);
        entity1.setType("Tipo1");
        entity1.setActive(true);

        TypeJpaEntity entity2 = new TypeJpaEntity();
        entity2.setId(2L);
        entity2.setType("Tipo2");
        entity2.setActive(false);

        List<TypeJpaEntity> entities = List.of(entity1, entity2);

        // WHEN
        List<Type> domains = mapper.toDomainList(entities);

        // THEN
        assertEquals(2, domains.size());
        assertEquals("Tipo1", domains.get(0).getType());
        assertEquals("Tipo2", domains.get(1).getType());
    }

    @Test
    @DisplayName("GIVEN list of Type domain WHEN toEntityList THEN should map all")
    void toEntityList_ValidDomains_ReturnsEntityList() {
        // GIVEN
        Type domain1 = new Type();
        domain1.setId(10L);
        domain1.setType("TipoA");
        domain1.setActive(true);

        Type domain2 = new Type();
        domain2.setId(20L);
        domain2.setType("TipoB");
        domain2.setActive(false);

        List<Type> domains = List.of(domain1, domain2);

        // WHEN
        List<TypeJpaEntity> entities = mapper.toEntityList(domains);

        // THEN
        assertEquals(2, entities.size());
        assertEquals("TipoA", entities.get(0).getType());
        assertEquals("TipoB", entities.get(1).getType());
    }
}

