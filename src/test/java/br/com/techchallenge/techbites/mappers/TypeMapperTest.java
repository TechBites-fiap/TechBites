package br.com.techchallenge.techbites.mappers;

import br.com.techchallenge.techbites.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.dtos.TypeResponseDTO;
import br.com.techchallenge.techbites.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.entities.Type;
import br.com.techchallenge.techbites.entities.User;
import br.com.techchallenge.techbites.entities.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("TypeMapper - Unit Tests")
public class TypeMapperTest {

    private TypeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TypeMapper();
    }

    @Test
    @DisplayName("Deve converter TypeRequestDTO para Type entity com sucesso")
    void shouldConvertToTypeEntitySuccessfully() {
        //GIVE
        TypeRequestDTO typeRequestDTO = new TypeRequestDTO(
                "Entrada"
        );

        //WHEN
        Type type = mapper.toEntity(typeRequestDTO);

        //THEN
        assertNotNull(type);
        assertEquals(typeRequestDTO.type(), type.getType());
        assertNotNull(type.getCreatedAt());
        assertNotNull(type.getLastUpdatedAt());
        assertTrue(type.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertTrue(type.getLastUpdatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    @DisplayName("Deve converter Type entity para TypeResponseDTO com sucesso")
    void shouldConvertToUserResponseDTOSuccessfully() {
        //GIVE
        Type typeEntity = new Type();
        typeEntity.setType("Entrada");
        typeEntity.setId(1L);
        typeEntity.setCreatedAt(LocalDateTime.now());
        typeEntity.setLastUpdatedAt(LocalDateTime.now());
        typeEntity.setActive(true);

        //WHEN
        TypeResponseDTO typeResponseDTO = mapper.toDTO(typeEntity);

        //THEN
        assertNotNull(typeResponseDTO);
        assertEquals(typeResponseDTO.type(), typeEntity.getType());
        assertEquals(typeResponseDTO.id(), typeEntity.getId());
        assertEquals(typeResponseDTO.active(), typeEntity.getActive());

    }

    @Test
    @DisplayName("Deve converter uma lista de Type entities para uma lista de TypeResponseDTOs com sucesso")
    void shouldConvertListOfTypeEntitiesToListOfDTOsSuccessfully() {

        // GIVEN
        Type typeEntity1 = new Type();
        typeEntity1.setType("Entrada");
        typeEntity1.setId(1L);
        typeEntity1.setCreatedAt(LocalDateTime.now());
        typeEntity1.setLastUpdatedAt(LocalDateTime.now());
        typeEntity1.setActive(true);

        Type typeEntity2 = new Type();
        typeEntity2.setType("Entrada");
        typeEntity2.setId(2L);
        typeEntity2.setCreatedAt(LocalDateTime.now());
        typeEntity2.setLastUpdatedAt(LocalDateTime.now());
        typeEntity2.setActive(false);

        List<Type> types = Arrays.asList(typeEntity1, typeEntity2);

        //WHEN
        List<TypeResponseDTO> responseDTOs = mapper.toListDTO(types);

        TypeResponseDTO responseDTO1 = responseDTOs.get(0);
        assertNotNull(responseDTO1);
        assertEquals(typeEntity1.getType(), responseDTO1.type());

        assertNotNull(responseDTO1);
        assertEquals(responseDTO1.type(), typeEntity1.getType());
        assertEquals(responseDTO1.id(), typeEntity1.getId());
        assertEquals(responseDTO1.active(), typeEntity1.getActive());

        TypeResponseDTO responseDTO2 = responseDTOs.get(1);
        assertNotNull(responseDTO2);
        assertEquals(typeEntity2.getType(), responseDTO2.type());

        assertNotNull(responseDTO2);
        assertEquals(responseDTO2.type(), typeEntity2.getType());
        assertEquals(responseDTO2.id(), typeEntity2.getId());
        assertEquals(responseDTO2.active(), typeEntity2.getActive());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando toListDTO recebe uma lista vazia")
    void shouldReturnEmptyListWhenToListDTORecievesEmptyList() {
        // GIVEN
        List<Type> types = List.of();

        // WHEN
        List<TypeResponseDTO> responseDTOs = mapper.toListDTO(types);

        // THEN
        assertNotNull(responseDTOs);
        assertTrue(responseDTOs.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando toListDTO recebe null")
    void shouldReturnEmptyListWhenToListDTORecievesNull() {
        // GIVEN
        List<Type> types = null; // null

        // WHEN
        List<TypeResponseDTO> responseDTOs = mapper.toListDTO(types);

        // THEN
        assertNotNull(responseDTOs);
        assertTrue(responseDTOs.isEmpty());
    }

    @Test
    @DisplayName("Deve atualizar Type entity com dados de TypeRequestDTO com sucesso")
    void shouldUpdateUserEntitySuccessfully() {
        //GIVEN
        Type existingType = new Type();
        existingType.setId(1L);
        existingType.setType("Entrada");
        existingType.setCreatedAt(LocalDateTime.now().minusDays(1));
        existingType.setLastUpdatedAt(LocalDateTime.now().minusHours(1));
        existingType.setActive(true);

        TypeRequestDTO newData = new TypeRequestDTO(
                "Entrada"
        );

        // WHEN
        mapper.updateEntity(existingType, newData);

        //THEN
        assertEquals(existingType.getType(), newData.type());

        assertNotNull(existingType.getCreatedAt());
        assertTrue(existingType.getCreatedAt().isBefore(LocalDateTime.now().minusHours(23)));

        assertNotNull(existingType.getLastUpdatedAt());
        assertTrue(existingType.getLastUpdatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertNotEquals(LocalDateTime.now().minusHours(1), existingType.getLastUpdatedAt());
    }

}
