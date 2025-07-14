package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.*;
import br.com.techchallenge.techbites.entities.Type;
import br.com.techchallenge.techbites.entities.User;
import br.com.techchallenge.techbites.entities.enums.Role;
import br.com.techchallenge.techbites.mappers.TypeMapper;
import br.com.techchallenge.techbites.repositories.TypeRepository;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import br.com.techchallenge.techbites.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TypeService - Unit Tests")
public class TypeServiceTest {

    @Mock
    private TypeRepository repository;

    @Mock
    private TypeMapper mapper;

    @InjectMocks
    private TypeService service;

    private TypeRequestDTO typeRequestDTO;
    private TypeResponseDTO typeResponseDTO;
    private Type typeEntity;
    private Long typeId;

    @BeforeEach
    void setUp() {
        typeId = 1L;

        typeRequestDTO = new TypeRequestDTO(
                "Entrada"
        );

        typeResponseDTO = new TypeResponseDTO(
                typeId,
                "Entrada",
                true
        );

        typeEntity = new Type();
        typeEntity.setType("Entrada");
        typeEntity.setId(typeId);
        typeEntity.setCreatedAt(LocalDateTime.now());
        typeEntity.setLastUpdatedAt(LocalDateTime.now());
        typeEntity.setActive(true);
    }

    @Test
    @DisplayName("Deve criar um type com sucesso")
    void shouldCreateTypeSuccessfully() {
        //GIVEN
        when(mapper.toEntity(any())).thenReturn(typeEntity);
        when(repository.save(any(Type.class))).thenReturn(typeEntity);

        //WHEN
        Type result = service.createType(typeRequestDTO);

        //THEN
        assertNotNull(result);
        assertEquals(typeRequestDTO.type(), result.getType());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getLastUpdatedAt());
        assertEquals(true, result.getActive());

        verify(repository, times(1)).save(typeEntity);
        verify(mapper, times(1)).toEntity(typeRequestDTO);

    }

    @Test
    @DisplayName("Deve retornar todos os types ativos")
    void shouldFindAllActiveUsers() {
        // GIVEN
        Type type1 = new Type();
        type1.setType("Entrada");
        type1.setId(1L);
        type1.setCreatedAt(LocalDateTime.now());
        type1.setLastUpdatedAt(LocalDateTime.now());
        type1.setActive(true);

        Type type2 = new Type();
        type2.setType("Entrada");
        type2.setId(2L);
        type2.setCreatedAt(LocalDateTime.now());
        type2.setLastUpdatedAt(LocalDateTime.now());
        type2.setActive(false);

        List<Type> activeTypesEntities = List.of(type1, type2);
        List<TypeResponseDTO> activeTypesDTOs = List.of(
                new TypeResponseDTO(type1.getId() , type1.getType() , type1.getActive())
        );

        when(repository.findByActive(eq(true), any(Sort.class))).thenReturn(activeTypesEntities);
        when(mapper.toListDTO(activeTypesEntities)).thenReturn(activeTypesDTOs);

        //WHEN
        List<TypeResponseDTO> result = service.findAllType(true);

        //THEN
        assertNotNull(result);

        assertEquals(activeTypesDTOs.size(), result.size());
        assertEquals(activeTypesEntities.get(0).getType(), result.get(0).type());
        assertEquals(activeTypesEntities.get(0).getId(), result.get(0).id());
        assertTrue(result.get(0).active());

        verify(repository, times(1)).findByActive(eq(true), any(Sort.class));
        verify(mapper, times(1)).toListDTO(activeTypesEntities);

    }

    @Test
    @DisplayName("Deve retornar todos os types inativos")
    void shouldFindAllInactiveUsers() {
        // GIVEN
        Type type1 = new Type();
        type1.setType("Entrada");
        type1.setId(1L);
        type1.setCreatedAt(LocalDateTime.now());
        type1.setLastUpdatedAt(LocalDateTime.now());
        type1.setActive(true);

        Type type2 = new Type();
        type2.setType("Entrada");
        type2.setId(2L);
        type2.setCreatedAt(LocalDateTime.now());
        type2.setLastUpdatedAt(LocalDateTime.now());
        type2.setActive(false);

        List<Type> inactiveTypesEntities = List.of(type2);
        List<TypeResponseDTO> inactiveTypesDTOs = List.of(
                new TypeResponseDTO(type2.getId() , type2.getType() , type2.getActive())
        );

        when(repository.findByActive(eq(false), any(Sort.class))).thenReturn(inactiveTypesEntities);
        when(mapper.toListDTO(inactiveTypesEntities)).thenReturn(inactiveTypesDTOs);

        //WHEN
        List<TypeResponseDTO> result = service.findAllType(false);

        //THEN
        assertNotNull(result);

        assertEquals(inactiveTypesDTOs.size(), result.size());
        assertEquals(inactiveTypesEntities.get(0).getType(), result.get(0).type());
        assertEquals(inactiveTypesEntities.get(0).getId(), result.get(0).id());
        assertFalse(result.get(0).active());

        verify(repository, times(1)).findByActive(eq(false), any(Sort.class));
        verify(mapper, times(1)).toListDTO(inactiveTypesEntities);

    }

    @Test
    @DisplayName("Deve retornar todos os types (ativos e inativos) quando 'active' for nulo")
    void shouldFindAllUsersWhenActiveIsNull() {
        // GIVEN
        Type type1 = new Type();
        type1.setType("Entrada");
        type1.setId(1L);
        type1.setCreatedAt(LocalDateTime.now());
        type1.setLastUpdatedAt(LocalDateTime.now());
        type1.setActive(true);

        Type type2 = new Type();
        type2.setType("Entrada");
        type2.setId(2L);
        type2.setCreatedAt(LocalDateTime.now());
        type2.setLastUpdatedAt(LocalDateTime.now());
        type2.setActive(false);

        List<Type> allTypesEntities = List.of(type1, type2);
        List<TypeResponseDTO> allTypesDTOs = List.of(
                new TypeResponseDTO(type1.getId() , type1.getType() , type1.getActive()),
                new TypeResponseDTO(type2.getId() , type2.getType() , type2.getActive())
        );

        when(repository.findAll(any(Sort.class))).thenReturn(allTypesEntities);
        when(mapper.toListDTO(allTypesEntities)).thenReturn(allTypesDTOs);

        //WHEN
        List<TypeResponseDTO> result = service.findAllType(null);

        //THEN
        assertNotNull(result);

        assertEquals(allTypesDTOs.size(), result.size());

        assertEquals(allTypesEntities.get(0).getType(), result.get(0).type());
        assertEquals(allTypesEntities.get(0).getId(), result.get(0).id());
        assertEquals(allTypesEntities.get(0).getActive(), result.get(0).active());

        assertEquals(allTypesEntities.get(1).getType(), result.get(1).type());
        assertEquals(allTypesEntities.get(1).getActive(), result.get(1).active());


        verify(repository, times(1)).findAll(any(Sort.class));
        verify(mapper, times(1)).toListDTO(allTypesEntities);

    }

    @Test
    @DisplayName("Deve encontrar Type por ID com sucesso")
    void shouldFindTypeByIdSuccessfully() {
        //GIVEN
        when(repository.findById(eq(typeId))).thenReturn(Optional.of(typeEntity));
        when(mapper.toDTO(any(Type.class))).thenReturn(typeResponseDTO);

        //WHEN
        Optional<TypeResponseDTO> result = service.findTypeById(typeId);

        //THEN
        assertNotNull(result);
        assertEquals(typeEntity.getId() , typeId);
        assertEquals(typeEntity.getType() , result.get().type());

        verify(repository, times(1)).findById(eq(typeId));
        verify(mapper, times(1)).toDTO(any(Type.class));

    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao buscar Type por ID inexistente")
    void shouldThrowExceptionWhenTypeNotFound() {
        // GIVEN
        when(repository.findById(eq(typeId))).thenReturn(Optional.empty());

        // WHEN AND THEN
        assertThrows(ResourceNotFoundException.class, () -> service.findTypeById(typeId));

        verify(repository, times(1)).findById(eq(typeId));
        verify(mapper, never()).toDTO(any(Type.class));
    }

    @Test
    @DisplayName("Deve deletar type por ID com sucesso")
    void shouldDeleteTypeByIdSuccessfully() {
        // GIVEN
        when(repository.findById(eq(typeId))).thenReturn(Optional.of(typeEntity));
        doNothing().when(repository).delete(any(Type.class));

        // WHEN
        service.deleteTypeById(typeId);

        // THEN
        verify(repository, times(1)).findById(eq(typeId));
        verify(repository, times(1)).delete(typeEntity);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao deletar Type por ID inexistente")
    void shouldThrowExceptionWhenIdNotFoundWhenDeleting() {
        // GIVEN
        when(repository.findById(eq(typeId))).thenReturn(Optional.empty());

        // WHEN AND THEN
        assertThrows(ResourceNotFoundException.class, () -> service.deleteTypeById(typeId));

        verify(repository, times(1)).findById(eq(typeId));
        verify(repository, never()).delete(any(Type.class));
    }

    @Test
    @DisplayName("Deve habilitar type por ID com sucesso")
    void shouldEnableTypeByIdSuccessfully() {
        // GIVEN
        when(repository.findById(eq(typeId))).thenReturn(Optional.of(typeEntity));
        when(repository.save(any(Type.class))).thenReturn(typeEntity);

        // WHEN
        service.enableTypeById(typeId);

        // THEN
        assertEquals(true, typeEntity.getActive());
        verify(repository, times(1)).findById(eq(typeId));
        verify(repository, times(1)).save(typeEntity);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar habilitar typer por ID")
    void shouldThrowExceptionWhenIdNotFoundWhenEnabling() {
        // GIVEN
        when(repository.findById(eq(typeId))).thenReturn(Optional.empty());

        // WHEN AND THEN
        assertThrows(ResourceNotFoundException.class, () -> service.enableTypeById(typeId));

        verify(repository, times(1)).findById(eq(typeId));
        verify(repository, never()).save(any(Type.class));
    }

    @Test
    @DisplayName("Deve atualizar Type por ID com sucesso")
    void shouldUpdateTypeByIdSuccessfully() {
        // GIVEN
        when(repository.findById(eq(typeId))).thenReturn(Optional.of(typeEntity));

        when(repository.save(any(Type.class)))
                .thenAnswer(invocation -> {
                    Type savedType = invocation.getArgument(0);
                    return savedType;
                });

        TypeResponseDTO expectedUpdatedResponseDTO = new TypeResponseDTO(
                typeId,
                typeRequestDTO.type(),
                true
        );

        when(mapper.toDTO(any(Type.class))).thenReturn(expectedUpdatedResponseDTO);

        // WHEN
        TypeResponseDTO result = service.updateTypeById(typeId, typeRequestDTO);

        // THEN
        assertNotNull(result);
        assertEquals(expectedUpdatedResponseDTO.id(), result.id());
        assertEquals(expectedUpdatedResponseDTO.type(), result.type());
        assertEquals(expectedUpdatedResponseDTO.active(), result.active());

        assertEquals(typeRequestDTO.type(), typeEntity.getType());
        assertTrue(typeEntity.getLastUpdatedAt().isAfter(LocalDateTime.now().minusSeconds(5)));
        assertTrue(typeEntity.getLastUpdatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));

        verify(repository, times(1)).findById(eq(typeId));
        verify(repository, times(1)).save(typeEntity);
        verify(mapper, times(1)).toDTO(typeEntity);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar Type por ID inexistente")
    void shouldThrowExceptionWhenIdNotFoundWhenUpdating() {
        // GIVEN
        when(repository.findById(eq(typeId))).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> service.updateTypeById(typeId, typeRequestDTO));

        verify(repository, times(1)).findById(eq(typeId));
        verify(repository, never()).save(any(Type.class));
        verify(mapper, never()).toDTO(any(Type.class));
    }



}
