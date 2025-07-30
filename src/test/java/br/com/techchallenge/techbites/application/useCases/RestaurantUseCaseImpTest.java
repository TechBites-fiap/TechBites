package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.AddressGateway;
import br.com.techchallenge.techbites.application.gateways.RestaurantGateway;
import br.com.techchallenge.techbites.application.gateways.UserGateway;
import br.com.techchallenge.techbites.domain.enums.Role;
import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.domain.models.User;
import br.com.techchallenge.techbites.infrastructure.gateways.AddressEntityMapper;
import br.com.techchallenge.techbites.infrastructure.gateways.RestaurantEntityMapper;
import br.com.techchallenge.techbites.infrastructure.gateways.UserEntityMapper;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseImpTest {

    @Mock
    private RestaurantGateway repository;

    @Mock
    private RestaurantEntityMapper mapper;

    @Mock
    private UserGateway userRepository;

    @Mock
    private UserEntityMapper ownerMapper;

    @Mock
    private AddressGateway addressRepository;

    @Mock
    private AddressEntityMapper addressMapper;

    @InjectMocks
    private RestaurantUseCaseImp useCase;

    private RestaurantJpaEntity restaurantEntity;
    private UserJpaEntity userJpaEntity;
    private Restaurant restaurantDomain;
    private AddressJpaEntity addressJpaEntity;
    private Address addressDomain;
    private User userDomain;

    @BeforeEach
    void setUp() {
        userJpaEntity = new UserJpaEntity();
        userJpaEntity.setId(1L);
        userJpaEntity.setActive(true);

        userDomain = new User(1L, "João", "joao@email.com", "senha", Role.USER_RESTAURANT, LocalDateTime.now().minusDays(1), LocalDateTime.now(), true);

        addressJpaEntity = new AddressJpaEntity();
        addressJpaEntity.setId(10L);
        addressJpaEntity.setActive(true);

        addressDomain = new Address(1L ,"Rua XPTO", "123", "Casa", "Bairro", "Cidade", "SP", "00000-000", LocalDateTime.now(), LocalDateTime.now(), true);

        restaurantEntity = new RestaurantJpaEntity();
        restaurantEntity.setId(100L);
        restaurantEntity.setOwner(userJpaEntity);
        restaurantEntity.setAddress(addressJpaEntity);
        restaurantEntity.setActive(true);

        restaurantDomain = new Restaurant(
                100L,
                "Boteco do Juca",
                "Comida boa",
                addressDomain,
                "Brasileira",
                "18:00",
                userDomain,
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now(),
                true
        );
    }

    @Test
    @DisplayName("GIVEN valid restaurant WHEN createRestaurant THEN save and return entity")
    void testCreateRestaurant() {
        // GIVEN
        when(userRepository.findUserById(userJpaEntity.getId())).thenReturn(Optional.of(userJpaEntity));
        when(ownerMapper.toDomain(userJpaEntity)).thenReturn(userDomain);
        when(addressMapper.toEntity(addressDomain)).thenReturn(addressJpaEntity);
        when(addressRepository.createAddress(addressJpaEntity)).thenReturn(addressJpaEntity);
        when(addressMapper.toDomain(addressJpaEntity)).thenReturn(addressDomain);
        when(mapper.toDomain(restaurantEntity)).thenReturn(restaurantDomain);
        when(mapper.toEntity(any(Restaurant.class))).thenReturn(restaurantEntity);
        when(repository.createRestaurant(restaurantEntity)).thenReturn(restaurantEntity);

        // WHEN
        RestaurantJpaEntity result = useCase.createRestaurant(restaurantEntity);

        // THEN
        assertNotNull(result);
        verify(userRepository).findUserById(userJpaEntity.getId());
        verify(addressRepository).createAddress(any());
        verify(repository).createRestaurant(any());
    }

    @Test
    @DisplayName("GIVEN inactive owner WHEN createRestaurant THEN throw ResourceNotFoundException")
    void testCreateRestaurant_InactiveOwner_Throws() {
        // GIVEN
        userJpaEntity.setActive(false);
        when(userRepository.findUserById(userJpaEntity.getId())).thenReturn(Optional.of(userJpaEntity));
        when(ownerMapper.toDomain(userJpaEntity)).thenReturn(new User(userJpaEntity.getId(), "João", "joao@email.com", "senha", Role.USER_RESTAURANT, LocalDateTime.now(), LocalDateTime.now(), false));

        // WHEN + THEN
        assertThrows(ResourceNotFoundException.class, () -> useCase.createRestaurant(restaurantEntity));
    }

    @Test
    @DisplayName("GIVEN active filter WHEN getAllRestaurants THEN return list")
    void testGetAllRestaurants() {
        // GIVEN
        List<RestaurantJpaEntity> list = List.of(restaurantEntity);
        when(repository.getAllRestaurants(true)).thenReturn(list);

        // WHEN
        List<RestaurantJpaEntity> result = useCase.getAllRestaurants(true);

        // THEN
        assertEquals(1, result.size());
        verify(repository).getAllRestaurants(true);
    }

    @Test
    @DisplayName("GIVEN valid id WHEN findRestaurantById THEN return Optional")
    void testFindRestaurantById() {
        // GIVEN
        when(repository.findRestaurantById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));

        // WHEN
        Optional<RestaurantJpaEntity> result = useCase.findRestaurantById(restaurantEntity.getId());

        // THEN
        assertTrue(result.isPresent());
        assertEquals(restaurantEntity, result.get());
    }

    @Test
    @DisplayName("GIVEN valid id WHEN deleteRestaurantById THEN call delete methods")
    void testDeleteRestaurantById() {
        // GIVEN
        when(repository.findRestaurantById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));

        // WHEN
        useCase.deleteRestaurantById(restaurantEntity.getId());

        // THEN
        verify(addressRepository).deleteAddressById(addressJpaEntity.getId());
        verify(repository).deleteRestaurantById(restaurantEntity.getId());
    }

    @Test
    @DisplayName("GIVEN valid id WHEN enableRestaurantById THEN activate entities")
    void testEnableRestaurantById() {
        // GIVEN
        when(repository.findRestaurantById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));

        // WHEN
        useCase.enableRestaurantById(restaurantEntity.getId());

        // THEN
        verify(addressRepository).enableAddress(addressJpaEntity);
        verify(repository).enableRestaurant(restaurantEntity);
        assertTrue(restaurantEntity.getActive());
        assertTrue(addressJpaEntity.getActive());
    }

    @Test
    @DisplayName("GIVEN valid update WHEN updateRestaurantById THEN update and return updated entity")
    void testUpdateRestaurantById() {
        // GIVEN
        when(repository.findRestaurantById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));
        when(userRepository.findUserById(userJpaEntity.getId())).thenReturn(Optional.of(userJpaEntity));
        when(mapper.toDomain(restaurantEntity)).thenReturn(restaurantDomain);
        when(mapper.toEntity(any(Restaurant.class))).thenReturn(restaurantEntity);
        when(addressRepository.updateAddress(any())).thenReturn(addressJpaEntity);
        when(repository.updateAddress(any())).thenReturn(restaurantEntity);

        RestaurantJpaEntity updateEntity = new RestaurantJpaEntity();
        updateEntity.setId(restaurantEntity.getId());
        updateEntity.setOwner(userJpaEntity);
        updateEntity.setAddress(addressJpaEntity);
        updateEntity.setName("Novo Nome");
        updateEntity.setCuisine("Nova Cozinha");
        updateEntity.setDescription("Nova Descrição");
        updateEntity.setOpeningHour("20:00");

        // WHEN
        RestaurantJpaEntity updated = useCase.updateRestaurantById(restaurantEntity.getId(), updateEntity);

        // THEN
        assertNotNull(updated);
        verify(repository).findRestaurantById(restaurantEntity.getId());
        verify(userRepository).findUserById(userJpaEntity.getId());
        verify(addressRepository).updateAddress(any());
        verify(repository).updateAddress(any());
    }
}



