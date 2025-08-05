package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeRepository;

import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TypeRepositoryGatewayTest {

    private TypeRepository repository;
    private TypeEntityMapper mapper;
    private TypeRepositoryGateway gateway;

    @BeforeEach
    void setUp() {
        repository = mock(TypeRepository.class);
        mapper = mock(TypeEntityMapper.class);
        gateway = new TypeRepositoryGateway(repository, mapper);
    }

    @Test
    @DisplayName("GIVEN entity WHEN createType THEN save and return entity")
    void testCreateType() {
        // GIVEN
        TypeJpaEntity entity = new TypeJpaEntity();
        when(repository.save(entity)).thenReturn(entity);

        // WHEN
        TypeJpaEntity result = gateway.createType(entity);

        // THEN
        verify(repository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    @DisplayName("GIVEN existing id WHEN findTypeById THEN return entity")
    void testFindTypeById_Exists() {
        // GIVEN
        Long id = 1L;
        TypeJpaEntity entity = new TypeJpaEntity();
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        // WHEN
        Optional<TypeJpaEntity> result = gateway.findTypeById(id);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    @DisplayName("GIVEN non-existing id WHEN findTypeById THEN throw exception")
    void testFindTypeById_NotExists() {
        // GIVEN
        Long id = 2L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // WHEN / THEN
        assertThrows(ResourceNotFoundException.class, () -> gateway.findTypeById(id));
    }

    @Test
    @DisplayName("GIVEN active filter null WHEN getAllTypes THEN return all sorted")
    void testGetAllTypes_ActiveNull() {
        // GIVEN
        List<TypeJpaEntity> entities = List.of(new TypeJpaEntity(), new TypeJpaEntity());
        when(repository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(entities);

        // WHEN
        List<TypeJpaEntity> result = gateway.getAllTypes(null);

        // THEN
        verify(repository).findAll(Sort.by(Sort.Direction.ASC, "id"));
        assertEquals(entities, result);
    }

    @Test
    @DisplayName("GIVEN active filter true WHEN getAllTypes THEN return filtered list")
    void testGetAllTypes_ActiveTrue() {
        // GIVEN
        List<TypeJpaEntity> entities = List.of(new TypeJpaEntity());
        when(repository.findByActive(Sort.by(Sort.Direction.ASC, "id"), true)).thenReturn(entities);

        // WHEN
        List<TypeJpaEntity> result = gateway.getAllTypes(true);

        // THEN
        verify(repository).findByActive(Sort.by(Sort.Direction.ASC, "id"), true);
        assertEquals(entities, result);
    }

    @Test
    @DisplayName("GIVEN existing id WHEN deleteTypeById THEN delete entity")
    void testDeleteTypeById() {
        // GIVEN
        Long id = 3L;
        TypeJpaEntity entity = new TypeJpaEntity();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(repository).delete(entity);

        // WHEN
        gateway.deleteTypeById(id);

        // THEN
        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("GIVEN non-existing id WHEN deleteTypeById THEN throw exception")
    void testDeleteTypeById_NotExists() {
        // GIVEN
        Long id = 4L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // WHEN / THEN
        assertThrows(ResourceNotFoundException.class, () -> gateway.deleteTypeById(id));
    }

    @Test
    @DisplayName("GIVEN existing id WHEN enableTypeById THEN set active true and save")
    void testEnableTypeById() {
        // GIVEN
        Long id = 5L;
        TypeJpaEntity entity = new TypeJpaEntity();
        entity.setActive(false);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        // WHEN
        gateway.enableTypeById(id);

        // THEN
        assertTrue(entity.getActive());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("GIVEN non-existing id WHEN enableTypeById THEN throw exception")
    void testEnableTypeById_NotExists() {
        // GIVEN
        Long id = 6L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // WHEN / THEN
        assertThrows(ResourceNotFoundException.class, () -> gateway.enableTypeById(id));
    }

    @Test
    @DisplayName("GIVEN entity WHEN updateType THEN save and return entity")
    void testUpdateType() {
        // GIVEN
        TypeJpaEntity entity = new TypeJpaEntity();
        when(repository.save(entity)).thenReturn(entity);

        // WHEN
        TypeJpaEntity result = gateway.updateType(entity);

        // THEN
        verify(repository).save(entity);
        assertEquals(entity, result);
    }

}

