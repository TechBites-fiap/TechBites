package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantRepository;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantRepositoryGatewayTest {

    @Mock
    private RestaurantRepository repository;

    private RestaurantRepositoryGateway gateway;

    private RestaurantJpaEntity entity;

    @BeforeEach
    void setUp() {
        gateway = new RestaurantRepositoryGateway(repository);

        entity = new RestaurantJpaEntity();
        entity.setId(1L);
    }

    @Test
    @DisplayName("GIVEN a restaurant entity WHEN createRestaurant THEN save and return the entity")
    void testCreateRestaurant() {
        // GIVEN
        when(repository.save(entity)).thenReturn(entity);

        // WHEN
        RestaurantJpaEntity result = gateway.createRestaurant(entity);

        // THEN
        assertNotNull(result);
        assertEquals(entity, result);
        verify(repository, times(1)).save(entity);
    }

    @Test
    @DisplayName("GIVEN active is null WHEN getAllRestaurants THEN findAll called and list returned")
    void testGetAllRestaurants_ActiveNull() {
        // GIVEN
        List<RestaurantJpaEntity> list = List.of(entity);
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        when(repository.findAll(sort)).thenReturn(list);

        // WHEN
        List<RestaurantJpaEntity> result = gateway.getAllRestaurants(null);

        // THEN
        assertEquals(list, result);
        verify(repository, times(1)).findAll(sort);
    }

    @Test
    @DisplayName("GIVEN active is true WHEN getAllRestaurants THEN findByActive called and list returned")
    void testGetAllRestaurants_ActiveTrue() {
        // GIVEN
        List<RestaurantJpaEntity> list = List.of(entity);
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        when(repository.findByActive(sort, true)).thenReturn(list);

        // WHEN
        List<RestaurantJpaEntity> result = gateway.getAllRestaurants(true);

        // THEN
        assertEquals(list, result);
        verify(repository, times(1)).findByActive(sort, true);
    }

    @Test
    @DisplayName("GIVEN existing id WHEN findRestaurantById THEN return Optional with entity")
    void testFindRestaurantById_Found() {
        // GIVEN
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        // WHEN
        Optional<RestaurantJpaEntity> result = gateway.findRestaurantById(entity.getId());

        // THEN
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(repository, times(1)).findById(entity.getId());
    }

    @Test
    @DisplayName("GIVEN non-existing id WHEN findRestaurantById THEN throw ResourceNotFoundException")
    void testFindRestaurantById_NotFound() {
        // GIVEN
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // WHEN / THEN
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> gateway.findRestaurantById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("GIVEN existing id WHEN deleteRestaurantById THEN delete method called")
    void testDeleteRestaurantById_Found() {
        // GIVEN
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        doNothing().when(repository).delete(entity);

        // WHEN
        gateway.deleteRestaurantById(entity.getId());

        // THEN
        verify(repository, times(1)).findById(entity.getId());
        verify(repository, times(1)).delete(entity);
    }

    @Test
    @DisplayName("GIVEN non-existing id WHEN deleteRestaurantById THEN throw ResourceNotFoundException")
    void testDeleteRestaurantById_NotFound() {
        // GIVEN
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // WHEN / THEN
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> gateway.deleteRestaurantById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("GIVEN a restaurant entity WHEN enableRestaurant THEN save method called")
    void testEnableRestaurant() {
        // WHEN
        gateway.enableRestaurant(entity);

        // THEN
        verify(repository, times(1)).save(entity);
    }

    @Test
    @DisplayName("GIVEN a restaurant entity WHEN updateAddress THEN save and return entity")
    void testUpdateAddress() {
        // GIVEN
        when(repository.save(entity)).thenReturn(entity);

        // WHEN
        RestaurantJpaEntity result = gateway.updateAddress(entity);

        // THEN
        assertNotNull(result);
        assertEquals(entity, result);
        verify(repository, times(1)).save(entity);
    }
}
