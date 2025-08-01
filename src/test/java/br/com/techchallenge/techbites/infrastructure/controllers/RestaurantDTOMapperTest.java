package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.domain.enums.Role;
import br.com.techchallenge.techbites.dtos.*;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantDTOMapperTest {

    @Mock
    private AddressDTOMapper addressMapper;

    @Mock
    private UserDTOMapper userMapper;

    @InjectMocks
    private RestaurantDTOMapper mapper;

    @Test
    @DisplayName("GIVEN valid RestaurantWithAddressRequestDTO WHEN toJpaEntity THEN return RestaurantJpaEntity")
    void testToJpaEntity_WithValidDTO() {
        // GIVEN
        AddressRequestDTO addressDTO = new AddressRequestDTO(
                "Rua XPTO",
                "123",
                "Casa 2",
                "Jardim Teste",
                "São Paulo",
                "SP",
                "01000-000"
        );
        RestaurantWithAddressRequestDTO dto = new RestaurantWithAddressRequestDTO(
                "Bar do Zé", "Melhor feijoada", "Brasileira", "Das 9:00 as 18:00 horas", 1L, addressDTO
        );

        AddressJpaEntity mappedAddress = new AddressJpaEntity();
        when(addressMapper.toJpaEntity(addressDTO)).thenReturn(mappedAddress);

        // WHEN
        RestaurantJpaEntity result = mapper.toJpaEntity(dto);

        // THEN
        assertEquals("Bar do Zé", result.getName());
        assertEquals("Melhor feijoada", result.getDescription());
        assertEquals("Brasileira", result.getCuisine());
        assertEquals("Das 9:00 as 18:00 horas", result.getOpeningHour());
        assertEquals(1L, result.getOwner().getId());
        assertEquals(mappedAddress, result.getAddress());
    }

    @Test
    @DisplayName("GIVEN null RestaurantJpaEntity WHEN toResponseDTO THEN return null")
    void testToResponseDTO_WithNullEntity() {
        // GIVEN
        RestaurantJpaEntity entity = null;

        // WHEN
        RestaurantWithAddressResponseDTO result = mapper.toResponseDTO(entity);

        // THEN
        assertNull(result);
    }

    @Test
    @DisplayName("GIVEN valid RestaurantJpaEntity WHEN toResponseDTO THEN return RestaurantWithAddressResponseDTO")
    void testToResponseDTO_WithValidEntity() {
        // GIVEN
        RestaurantJpaEntity entity = new RestaurantJpaEntity();
        entity.setId(10L);
        entity.setName("Cantina da Vó");
        entity.setDescription("Comida italiana raiz");
        entity.setCuisine("Italiana");
        entity.setOpeningHour(String.valueOf(LocalTime.of(11, 30)));
        entity.setActive(true);
        entity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 10, 0));
        entity.setLastUpdatedAt(LocalDateTime.of(2024, 1, 2, 12, 0));

        UserJpaEntity owner = new UserJpaEntity();
        owner.setId(1L);
        owner.setName("João Silva");
        owner.setEmail("joao@email.com");
        owner.setRole(Role.USER_RESTAURANT);
        owner.setCreatedAt(LocalDateTime.of(2024, 1, 1, 9, 0));
        owner.setLastUpdatedAt(LocalDateTime.of(2024, 1, 2, 10, 0));
        owner.setActive(true);
        entity.setOwner(owner);

        AddressJpaEntity address = new AddressJpaEntity();
        entity.setAddress(address);

        UserResponseDTO userDTO = new UserResponseDTO(
                1L, "João Silva", "joao@email.com", Role.USER_RESTAURANT,
                LocalDateTime.of(2024, 1, 1, 9, 0),
                LocalDateTime.of(2024, 1, 2, 10, 0),
                true
        );

        AddressResponseDTO addressDTO = new AddressResponseDTO(
                1L,
                "Rua XPTO",
                "123",
                "Casa 2",
                "Jardim Teste",
                "São Paulo",
                "SP",
                "01000-000",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(userMapper.toResponseDTO(owner)).thenReturn(userDTO);
        when(addressMapper.toResponseDTO(address)).thenReturn(addressDTO);

        // WHEN
        RestaurantWithAddressResponseDTO result = mapper.toResponseDTO(entity);

        // THEN
        assertNotNull(result);
        assertEquals("Cantina da Vó", result.name());
        assertEquals("Comida italiana raiz", result.description());
        assertEquals("Italiana", result.cuisine());
        assertEquals("11:30", result.openingHour());
        assertEquals(true, result.active());
        assertEquals(1L, result.owner().id());
        assertEquals("João Silva", result.owner().name());
        assertEquals("joao@email.com", result.owner().email());
        assertEquals("Rua XPTO", result.address().street());
    }

    @Test
    @DisplayName("GIVEN list of RestaurantJpaEntity WHEN toListResponseDTO THEN return list of DTOs")
    void testToListResponseDTO_WithEntities() {
        // GIVEN
        RestaurantJpaEntity entity = new RestaurantJpaEntity();
        entity.setId(1L);

        RestaurantWithAddressResponseDTO dto = new RestaurantWithAddressResponseDTO(
                1L, "Cantina da Vó", "Comida italiana raiz", "Italiana", "Das 9:00 as 18:00 horas",
                true, LocalDateTime.now(), LocalDateTime.now(),
                new UserResponseDTO(1L, "João Silva", "joao@email.com", Role.USER_RESTAURANT,
                        LocalDateTime.now(), LocalDateTime.now(), true),
                new AddressResponseDTO(
                        1L,
                        "Rua A",
                        "123",
                        "Complemento A",
                        "Bairro A",
                        "Cidade",
                        "UF",
                        "00000-000",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                )

        );

        when(userMapper.toResponseDTO(any())).thenReturn(dto.owner());
        when(addressMapper.toResponseDTO((AddressJpaEntity) any())).thenReturn(dto.address());

        List<RestaurantJpaEntity> list = List.of(entity);

        // WHEN
        List<RestaurantWithAddressResponseDTO> result = mapper.toListResponseDTO(list);

        // THEN
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
    }

    @Test
    @DisplayName("GIVEN empty optional WHEN toOpJpaEntity THEN return empty optional")
    void testToOpJpaEntity_WithEmptyOptional() {
        // GIVEN
        Optional<RestaurantJpaEntity> empty = Optional.empty();

        // WHEN
        Optional<RestaurantWithAddressResponseDTO> result = mapper.toOpJpaEntity(empty);

        // THEN
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("GIVEN optional with RestaurantJpaEntity WHEN toOpJpaEntity THEN return optional with DTO")
    void testToOpJpaEntity_WithValue() {
        // GIVEN
        RestaurantJpaEntity entity = new RestaurantJpaEntity();
        entity.setId(1L);

        RestaurantWithAddressResponseDTO dto = new RestaurantWithAddressResponseDTO(
                1L, "Cantina", "Descrição", "Italiana", "Das 9:00 as 18:00 horas",
                true, LocalDateTime.now(), LocalDateTime.now(),
                new UserResponseDTO(1L, "João Silva", "joao@email.com", Role.USER_RESTAURANT,
                        LocalDateTime.now(), LocalDateTime.now(), true),
                new AddressResponseDTO(
                        1L,
                        "Rua A",
                        "123",
                        "Complemento A",
                        "Bairro A",
                        "Cidade",
                        "UF",
                        "00000-000",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                )
        );

        when(userMapper.toResponseDTO(any())).thenReturn(dto.owner());
        when(addressMapper.toResponseDTO((AddressJpaEntity) any())).thenReturn(dto.address());

        // WHEN
        Optional<RestaurantWithAddressResponseDTO> result = mapper.toOpJpaEntity(Optional.of(entity));

        // THEN
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
    }
}


