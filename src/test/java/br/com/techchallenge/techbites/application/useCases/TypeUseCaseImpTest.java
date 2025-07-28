package br.com.techchallenge.techbites.application.useCases;



import br.com.techchallenge.techbites.application.gateways.TypeGateway;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.infrastructure.gateways.TypeEntityMapper;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TypeUseCaseImpTest {

    private TypeGateway repository;
    private TypeEntityMapper mapper;
    private TypeUseCaseImp useCase;

    @BeforeEach
    void setUp() {
        repository = mock(TypeGateway.class);
        mapper = mock(TypeEntityMapper.class);
        useCase = new TypeUseCaseImp(repository, mapper);
    }

    @Test
    @DisplayName("GIVEN a valid TypeJpaEntity WHEN createType THEN should return entity with timestamps and active true")
    void testCreateType() {
        // GIVEN
        TypeJpaEntity entity = new TypeJpaEntity();
        when(repository.createType(any())).thenAnswer(i -> i.getArgument(0));

        // WHEN
        TypeJpaEntity result = useCase.createType(entity);

        // THEN
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getLastUpdatedAt());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("GIVEN valid ID and active type WHEN findTypeById THEN should return the type")
    void testFindTypeById_WhenActive() {
        // GIVEN
        Long id = 1L;
        TypeJpaEntity entity = new TypeJpaEntity();
        Type domain = new Type();
        domain.setActive(true);
        when(repository.findTypeById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // WHEN
        Optional<TypeJpaEntity> result = useCase.findTypeById(id);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    @DisplayName("GIVEN valid ID and inactive type WHEN findTypeById THEN should throw ResourceNotFoundException")
    void testFindTypeById_WhenInactive() {
        // GIVEN
        Long id = 1L;
        TypeJpaEntity entity = new TypeJpaEntity();
        Type domain = new Type();
        domain.setActive(false);
        when(repository.findTypeById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // WHEN / THEN
        assertThrows(ResourceNotFoundException.class, () -> useCase.findTypeById(id));
    }

    @Test
    @DisplayName("GIVEN active flag WHEN getAllTypes THEN should return list from repository")
    void testGetAllTypes() {
        // GIVEN
        Boolean active = true;
        List<TypeJpaEntity> types = List.of(new TypeJpaEntity());
        when(repository.getAllTypes(active)).thenReturn(types);

        // WHEN
        List<TypeJpaEntity> result = useCase.getAllTypes(active);

        // THEN
        assertEquals(types, result);
    }

    @Test
    @DisplayName("GIVEN ID WHEN deleteTypeById THEN should call repository")
    void testDeleteTypeById() {
        // GIVEN
        Long id = 1L;

        // WHEN
        useCase.deleteTypeById(id);

        // THEN
        verify(repository).deleteTypeById(id);
    }

    @Test
    @DisplayName("GIVEN ID WHEN enableTypeById THEN should call repository")
    void testEnableTypeById() {
        // GIVEN
        Long id = 1L;

        // WHEN
        useCase.enableTypeById(id);

        // THEN
        verify(repository).enableTypeById(id);
    }

    @Test
    @DisplayName("GIVEN valid ID and active type WHEN updateTypeById THEN should update and return entity")
    void testUpdateTypeById_WhenActive() {
        // GIVEN
        Long id = 1L;
        TypeJpaEntity inputEntity = new TypeJpaEntity();
        inputEntity.setType("New Type");

        TypeJpaEntity foundEntity = new TypeJpaEntity();
        Type domain = new Type();
        domain.setActive(true);

        TypeJpaEntity updatedEntity = new TypeJpaEntity();

        when(repository.findTypeById(id)).thenReturn(Optional.of(foundEntity));
        when(mapper.toDomain(foundEntity)).thenReturn(domain);
        when(mapper.toEntity(domain)).thenReturn(updatedEntity);
        when(repository.updateType(updatedEntity)).thenReturn(updatedEntity);

        // WHEN
        TypeJpaEntity result = useCase.updateTypeById(id, inputEntity);

        // THEN
        assertEquals(updatedEntity, result);
    }

    @Test
    @DisplayName("GIVEN valid ID and inactive type WHEN updateTypeById THEN should throw ResourceNotFoundException")
    void testUpdateTypeById_WhenInactive() {
        // GIVEN
        Long id = 1L;
        TypeJpaEntity inputEntity = new TypeJpaEntity();
        TypeJpaEntity foundEntity = new TypeJpaEntity();
        Type domain = new Type();
        domain.setActive(false);

        when(repository.findTypeById(id)).thenReturn(Optional.of(foundEntity));
        when(mapper.toDomain(foundEntity)).thenReturn(domain);

        // WHEN / THEN
        assertThrows(ResourceNotFoundException.class, () -> useCase.updateTypeById(id, inputEntity));
    }
}


