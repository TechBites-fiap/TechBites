package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.TypeUseCase;
import br.com.techchallenge.techbites.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.dtos.TypeResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TypeControllerTest {

    @Mock
    private TypeUseCase useCase;

    @Mock
    private TypeDTOMapper mapper;

    @InjectMocks
    private TypeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createType_deveRetornar201() {
        TypeRequestDTO requestDTO = new TypeRequestDTO("Bebida");
        TypeJpaEntity entity = new TypeJpaEntity();
        TypeResponseDTO responseDTO = new TypeResponseDTO(1L, "Bebida", true);

        when(mapper.toJpaEntity(requestDTO)).thenReturn(entity);
        when(useCase.createType(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        ResponseEntity<TypeResponseDTO> response = controller.createType(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void findTypeById_deveRetornar200() {
        Long id = 1L;
        TypeJpaEntity entity = new TypeJpaEntity();
        TypeResponseDTO responseDTO = new TypeResponseDTO(1L, "Lanche", true);

        when(useCase.findTypeById(id)).thenReturn(Optional.of(entity));
        when(mapper.toOpResponseDTO(Optional.of(entity))).thenReturn(Optional.of(responseDTO));

        ResponseEntity<Optional<TypeResponseDTO>> response = controller.findTypeById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals("Lanche", response.getBody().get().type());
    }

    @Test
    void getAllTypes_deveRetornar200() {
        TypeJpaEntity entity = new TypeJpaEntity();
        TypeResponseDTO responseDTO = new TypeResponseDTO(1L, "Bebida", true);

        when(useCase.getAllTypes(null)).thenReturn(List.of(entity));
        when(mapper.toListResponseDTO(List.of(entity))).thenReturn(List.of(responseDTO));

        ResponseEntity<List<TypeResponseDTO>> response = controller.getAllTypes(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Bebida", response.getBody().get(0).type());
    }

    @Test
    void deleteTypeById_deveRetornar204() {
        Long id = 1L;
        doNothing().when(useCase).deleteTypeById(id);

        ResponseEntity<Void> response = controller.deleteTypeById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(useCase, times(1)).deleteTypeById(id);
    }

    @Test
    void enableTypeById_deveRetornar204() {
        Long id = 1L;
        doNothing().when(useCase).enableTypeById(id);

        ResponseEntity<Void> response = controller.enableTypeById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(useCase, times(1)).enableTypeById(id);
    }

    @Test
    void updateTypeById_deveRetornar200() {
        Long id = 1L;
        TypeRequestDTO requestDTO = new TypeRequestDTO("Doces");
        TypeJpaEntity entity = new TypeJpaEntity();
        TypeResponseDTO responseDTO = new TypeResponseDTO(1L, "Doces", true);

        when(mapper.toJpaEntity(requestDTO)).thenReturn(entity);
        when(useCase.updateTypeById(id, entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        ResponseEntity<TypeResponseDTO> response = controller.updateTypeById(id, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Doces", response.getBody().type());
    }
}

