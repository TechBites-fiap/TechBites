package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.enums.Role;
import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.domain.models.User;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantEntityMapperTest {

    @Mock
    private UserEntityMapper ownerMapper;

    @Mock
    private AddressEntityMapper addressMapper;

    private RestaurantEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new RestaurantEntityMapper(ownerMapper, addressMapper);
    }

    @Test
    @DisplayName("GIVEN null entity WHEN toDomain THEN return null")
    void testToDomain_WithNullEntity() {
        // GIVEN
        RestaurantJpaEntity entity = null;

        // WHEN
        Restaurant result = mapper.toDomain(entity);

        // THEN
        assertNull(result);
    }

    @Test
    @DisplayName("GIVEN valid entity WHEN toDomain THEN map fields correctly")
    void testToDomain_WithValidEntity() {
        // GIVEN
        RestaurantJpaEntity entity = new RestaurantJpaEntity();
        entity.setId(1L);
        entity.setName("Boteco do Juca");
        entity.setDescription("Comida raiz");
        entity.setCuisine("Brasileira");
        entity.setOpeningHour("Das 9:00 as 18:00 horas");
        entity.setCreatedAt(LocalDateTime.now().minusDays(1));
        entity.setLastUpdatedAt(LocalDateTime.now());
        entity.setActive(true);

        Address addressDomain = new Address( 1L ,"Rua X", "123", "Casa", "Bairro Y", "São Paulo", "SP", "00000-000",  LocalDateTime.now(), LocalDateTime.now() , true);
        User ownerDomain = new User(10L, "João", "joao@email.com", "senha", Role.USER, LocalDateTime.now(), LocalDateTime.now(), true);

        when(addressMapper.toDomain(entity.getAddress())).thenReturn(addressDomain);
        when(ownerMapper.toDomain(entity.getOwner())).thenReturn(ownerDomain);

        // WHEN
        Restaurant domain = mapper.toDomain(entity);

        // THEN
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getDescription(), domain.getDescription());
        assertEquals(entity.getCuisine(), domain.getCuisine());
        assertEquals(entity.getOpeningHour(), domain.getOpeningHour());
        assertEquals(addressDomain, domain.getAddress());
        assertEquals(ownerDomain, domain.getOwner());
        assertEquals(entity.getCreatedAt(), domain.getCreatedAt());
        assertEquals(entity.getLastUpdatedAt(), domain.getLastUpdatedAt());
        assertEquals(entity.getActive(), domain.getActive());
    }

    @Test
    @DisplayName("GIVEN null domain WHEN toEntity THEN return null")
    void testToEntity_WithNullDomain() {
        // GIVEN
        Restaurant domain = null;

        // WHEN
        RestaurantJpaEntity result = mapper.toEntity(domain);

        // THEN
        assertNull(result);
    }

    @Test
    @DisplayName("GIVEN valid domain WHEN toEntity THEN map fields correctly")
    void testToEntity_WithValidDomain() {
        // GIVEN
        AddressJpaEntity addressEntity = new AddressJpaEntity();
        UserJpaEntity ownerEntity = new UserJpaEntity();

        Address addressDomain = new Address( 1L ,"Rua X", "123", "Casa", "Bairro Y", "São Paulo", "SP", "00000-000",  LocalDateTime.now(), LocalDateTime.now() , true);
        User ownerDomain = new User(10L, "João", "joao@email.com", "senha", Role.USER_RESTAURANT, LocalDateTime.now(), LocalDateTime.now(), true);

        when(addressMapper.toEntity(addressDomain)).thenReturn(addressEntity);
        when(ownerMapper.toEntity(ownerDomain)).thenReturn(ownerEntity);

        Restaurant domain = new Restaurant(
                1L,
                "Boteco do Juca",
                "Comida raiz",
                addressDomain,
                "Brasileira",
                "Das 9:00 as 18:00 horas",
                ownerDomain,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(),
                true
        );

        // WHEN
        RestaurantJpaEntity entity = mapper.toEntity(domain);

        // THEN
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getDescription(), entity.getDescription());
        assertEquals(domain.getCuisine(), entity.getCuisine());
        assertEquals(domain.getOpeningHour(), entity.getOpeningHour());
        assertEquals(domain.getActive(), entity.getActive());
        assertEquals(domain.getCreatedAt(), entity.getCreatedAt());
        assertEquals(domain.getLastUpdatedAt(), entity.getLastUpdatedAt());
        assertEquals(addressEntity, entity.getAddress());
        assertEquals(ownerEntity, entity.getOwner());
    }
}

