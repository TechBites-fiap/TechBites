package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.RestaurantUseCase;
import br.com.techchallenge.techbites.dtos.RestaurantWithAddressRequestDTO;
import br.com.techchallenge.techbites.dtos.RestaurantWithAddressResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private RestaurantUseCase useCase;

    @Mock
    private RestaurantDTOMapper mapper;

    private RestaurantController controller;

    @BeforeEach
    void setUp() {
        controller = new RestaurantController(useCase, mapper);
    }

    @Test
    @DisplayName("GIVEN valid DTO WHEN POST /restaurants THEN create and return created restaurant")
    void testCreateRestaurant() {
        // GIVEN
        RestaurantWithAddressRequestDTO requestDTO = mock(RestaurantWithAddressRequestDTO.class);
        RestaurantJpaEntity entity = mock(RestaurantJpaEntity.class);
        RestaurantJpaEntity createdEntity = mock(RestaurantJpaEntity.class);
        RestaurantWithAddressResponseDTO responseDTO = mock(RestaurantWithAddressResponseDTO.class);

        when(mapper.toJpaEntity(requestDTO)).thenReturn(entity);
        when(useCase.createRestaurant(entity)).thenReturn(createdEntity);
        when(mapper.toResponseDTO(createdEntity)).thenReturn(responseDTO);

        // WHEN
        ResponseEntity<RestaurantWithAddressResponseDTO> response = controller.createRestaurant(requestDTO);

        // THEN
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(mapper).toJpaEntity(requestDTO);
        verify(useCase).createRestaurant(entity);
        verify(mapper).toResponseDTO(createdEntity);
    }

    @Test
    @DisplayName("GIVEN active filter WHEN GET /restaurants THEN return list of restaurants")
    void testGetAllRestaurants() {
        // GIVEN
        Boolean active = true;
        List<RestaurantJpaEntity> entities = List.of(mock(RestaurantJpaEntity.class));
        List<RestaurantWithAddressResponseDTO> dtos = List.of(mock(RestaurantWithAddressResponseDTO.class));

        when(useCase.getAllRestaurants(active)).thenReturn(entities);
        when(mapper.toListResponseDTO(entities)).thenReturn(dtos);

        // WHEN
        ResponseEntity<List<RestaurantWithAddressResponseDTO>> response = controller.getAllRestaurants(active);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
        verify(useCase).getAllRestaurants(active);
        verify(mapper).toListResponseDTO(entities);
    }

    @Test
    @DisplayName("GIVEN valid id WHEN GET /restaurants/{id} THEN return optional restaurant")
    void testFindRestaurantById() {
        // GIVEN
        Long id = 123L;
        Optional<RestaurantJpaEntity> entityOpt = Optional.of(mock(RestaurantJpaEntity.class));
        Optional<RestaurantWithAddressResponseDTO> dtoOpt = Optional.of(mock(RestaurantWithAddressResponseDTO.class));

        when(useCase.findRestaurantById(id)).thenReturn(entityOpt);
        when(mapper.toOpJpaEntity(entityOpt)).thenReturn(dtoOpt);

        // WHEN
        ResponseEntity<Optional<RestaurantWithAddressResponseDTO>> response = controller.findRestaurantById(id);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtoOpt, response.getBody());
        verify(useCase).findRestaurantById(id);
        verify(mapper).toOpJpaEntity(entityOpt);
    }

    @Test
    @DisplayName("GIVEN valid id WHEN DELETE /restaurants/{id} THEN delete restaurant and return no content")
    void testDeleteRestaurantById() {
        // GIVEN
        Long id = 55L;
        doNothing().when(useCase).deleteRestaurantById(id);

        // WHEN
        ResponseEntity<Void> response = controller.deleteRestaurantById(id);

        // THEN
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(useCase).deleteRestaurantById(id);
    }

    @Test
    @DisplayName("GIVEN valid id WHEN PATCH /restaurants/enable/{id} THEN enable restaurant and return no content")
    void testEnableRestaurantById() {
        // GIVEN
        Long id = 99L;
        doNothing().when(useCase).enableRestaurantById(id);

        // WHEN
        ResponseEntity<Void> response = controller.enableRestaurantById(id);

        // THEN
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(useCase).enableRestaurantById(id);
    }

    @Test
    @DisplayName("GIVEN valid id and DTO WHEN PUT /restaurants/{id} THEN update restaurant and return updated DTO")
    void testUpdateTypeById() {
        // GIVEN
        Long id = 77L;
        RestaurantWithAddressRequestDTO requestDTO = mock(RestaurantWithAddressRequestDTO.class);
        RestaurantJpaEntity entity = mock(RestaurantJpaEntity.class);
        RestaurantJpaEntity updatedEntity = mock(RestaurantJpaEntity.class);
        RestaurantWithAddressResponseDTO responseDTO = mock(RestaurantWithAddressResponseDTO.class);

        when(mapper.toJpaEntity(requestDTO)).thenReturn(entity);
        when(useCase.updateRestaurantById(id, entity)).thenReturn(updatedEntity);
        when(mapper.toResponseDTO(updatedEntity)).thenReturn(responseDTO);

        // WHEN
        ResponseEntity<RestaurantWithAddressResponseDTO> response = controller.updateTypeById(id, requestDTO);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(mapper).toJpaEntity(requestDTO);
        verify(useCase).updateRestaurantById(id, entity);
        verify(mapper).toResponseDTO(updatedEntity);
    }
}

