package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuItemJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MenuItemEntityMapperTest {

    @Mock
    private MenuEntityMapper menuMapper;
    @Mock
    private TypeEntityMapper typeMapper;
    @Mock
    private RestaurantEntityMapper restaurantMapper;

    @InjectMocks
    private MenuItemEntityMapper menuItemEntityMapper;

    private MenuItem menuItemDomain;
    private MenuItemJpaEntity menuItemEntity;
    private Menu menuDomain;
    private MenuJpaEntity menuEntity;
    private Type typeDomain;
    private TypeJpaEntity typeEntity;
    private Restaurant restaurantDomain;
    private RestaurantJpaEntity restaurantEntity;

    @BeforeEach
    void setUp() {
        menuDomain = new Menu();
        menuEntity = new MenuJpaEntity();
        typeDomain = new Type();
        typeEntity = new TypeJpaEntity();
        restaurantDomain = new Restaurant();
        restaurantEntity = new RestaurantJpaEntity();

        menuEntity.setRestaurant(restaurantEntity);

        menuItemDomain = new MenuItem();
        menuItemDomain.setId(1L);
        menuItemDomain.setName("Item Domain");
        menuItemDomain.setMenu(menuDomain);
        menuItemDomain.setItemType(typeDomain);

        menuItemEntity = new MenuItemJpaEntity();
        menuItemEntity.setId(1L);
        menuItemEntity.setName("Item Entity");
        menuItemEntity.setMenu(menuEntity);
        menuItemEntity.setItemType(typeEntity);

        when(menuMapper.toEntity(any(Menu.class))).thenReturn(menuEntity);
        when(typeMapper.toDomain(any(TypeJpaEntity.class))).thenReturn(typeDomain);
        when(typeMapper.toEntity(any(Type.class))).thenReturn(typeEntity);
        when(restaurantMapper.toDomain(any(RestaurantJpaEntity.class))).thenReturn(restaurantDomain);
    }

    @Test
    @DisplayName("Deve mapear de Entidade para Domínio (com SimpleMenu)")
    void toDomain() {
        MenuItem result = menuItemEntityMapper.toDomain(menuItemEntity);

        assertEquals(menuItemEntity.getId(), result.getId());
        assertEquals(menuItemEntity.getName(), result.getName());
        assertNotNull(result.getMenu());
        assertNotNull(result.getItemType());

        assertNull(result.getMenu().getItems());
    }

    @Test
    @DisplayName("Deve retornar nulo ao mapear Entidade nula para Domínio")
    void toDomain_withNullEntity() {
        assertNull(menuItemEntityMapper.toDomain(null));
    }

    @Test
    @DisplayName("Deve mapear de Entidade para Domínio com Menu nulo")
    void toDomain_withNullMenu() {
        menuItemEntity.setMenu(null);
        MenuItem result = menuItemEntityMapper.toDomain(menuItemEntity);
        assertNull(result.getMenu());
    }

    @Test
    @DisplayName("Deve mapear de Domínio para Entidade")
    void toEntity() {
        MenuItemJpaEntity result = menuItemEntityMapper.toEntity(menuItemDomain);

        assertEquals(menuItemDomain.getId(), result.getId());
        assertEquals(menuItemDomain.getName(), result.getName());
        assertNotNull(result.getMenu());
        assertNotNull(result.getItemType());
    }

    @Test
    @DisplayName("Deve retornar nulo ao mapear Domínio nulo para Entidade")
    void toEntity_withNullDomain() {
        assertNull(menuItemEntityMapper.toEntity(null));
    }
}