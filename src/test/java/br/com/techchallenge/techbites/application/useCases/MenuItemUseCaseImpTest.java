package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.MenuItemGateway;
import br.com.techchallenge.techbites.application.gateways.TypeGateway;
import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuItemEnableDTO;
import br.com.techchallenge.techbites.infrastructure.gateways.TypeEntityMapper;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemUseCaseImpTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private MenuUseCase menuUseCase;

    @Mock
    private TypeGateway typeGateway;

    @Mock
    private TypeEntityMapper typeMapper;

    @InjectMocks
    private MenuItemUseCaseImp menuItemUseCase;

    private Menu menu;
    private Type itemType;
    private MenuItem menuItem;
    private MenuItemCreateDTO menuItemCreateDTO;
    private MenuItemEnableDTO menuItemEnableDTO;

    @BeforeEach
    void setUp() {
        menu = new Menu();
        menu.setId(1L);
        menu.setActive(true);

        itemType = new Type();
        itemType.setId(1L);
        itemType.setActive(true);

        menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setActive(true);
        menuItem.setMenu(menu);

        menuItemCreateDTO = new MenuItemCreateDTO();
        menuItemCreateDTO.setName("New Item");
        menuItemCreateDTO.setDescription("Item Description");
        menuItemCreateDTO.setPrice(BigDecimal.TEN);
        menuItemCreateDTO.setPicturePath("/path/to/pic");
        menuItemCreateDTO.setMenuId(1L);
        menuItemCreateDTO.setItemTypeId(1L);

        menuItemEnableDTO = new MenuItemEnableDTO();
        menuItemEnableDTO.setPrice(BigDecimal.valueOf(15.00));
    }

    @Test
    @DisplayName("Deve criar um item de menu com sucesso")
    void create_Success() {
        when(menuUseCase.findById(1L)).thenReturn(menu);
        when(typeGateway.findTypeById(1L)).thenReturn(Optional.of(new br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity()));
        when(typeMapper.toDomain(any())).thenReturn(itemType);
        when(menuItemGateway.save(any(MenuItem.class))).thenAnswer(invocation -> {
            MenuItem item = invocation.getArgument(0);
            item.setId(2L); // Simulate saving and getting an ID
            return item;
        });

        MenuItem createdMenuItem = menuItemUseCase.create(menuItemCreateDTO);

        assertNotNull(createdMenuItem);
        assertEquals(menuItemCreateDTO.getName(), createdMenuItem.getName());
        assertEquals(menu, createdMenuItem.getMenu());
        assertEquals(itemType, createdMenuItem.getItemType());
        assertTrue(createdMenuItem.getActive());
        verify(menuUseCase).findById(1L);
        verify(typeGateway).findTypeById(1L);
        verify(menuItemGateway).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao criar item para menu inativo")
    void create_InactiveMenu() {
        menu.setActive(false);
        when(menuUseCase.findById(1L)).thenReturn(menu);

        assertThrows(ResourceNotFoundException.class, () -> menuItemUseCase.create(menuItemCreateDTO));
        verify(menuUseCase).findById(1L);
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao criar item para tipo de item inativo")
    void create_InactiveItemType() {
        itemType.setActive(false);
        when(menuUseCase.findById(1L)).thenReturn(menu);
        when(typeGateway.findTypeById(1L)).thenReturn(Optional.of(new br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity()));
        when(typeMapper.toDomain(any())).thenReturn(itemType);

        assertThrows(ResourceNotFoundException.class, () -> menuItemUseCase.create(menuItemCreateDTO));
        verify(menuUseCase).findById(1L);
        verify(typeGateway).findTypeById(1L);
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando o tipo de item não é encontrado")
    void create_ItemTypeNotFound() {
        when(menuUseCase.findById(1L)).thenReturn(menu);
        when(typeGateway.findTypeById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuItemUseCase.create(menuItemCreateDTO));
        verify(menuUseCase).findById(1L);
        verify(typeGateway).findTypeById(1L);
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve encontrar um item de menu pelo ID com sucesso")
    void findById_Success() {
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(menuItem));

        MenuItem foundItem = menuItemUseCase.findById(1L);

        assertNotNull(foundItem);
        assertEquals(menuItem.getId(), foundItem.getId());
        verify(menuItemGateway).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao procurar item de menu com ID inexistente")
    void findById_NotFound() {
        when(menuItemGateway.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuItemUseCase.findById(1L));
        verify(menuItemGateway).findById(1L);
    }

    @Test
    @DisplayName("Deve encontrar itens de menu pelo ID do menu")
    void findByMenuId_Success() {
        when(menuItemGateway.findByMenuId(1L)).thenReturn(Collections.singletonList(menuItem));

        List<MenuItem> items = menuItemUseCase.findByMenuId(1L);

        assertNotNull(items);
        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
        verify(menuItemGateway).findByMenuId(1L);
    }

    @Test
    @DisplayName("Deve deletar um item de menu com sucesso")
    void delete_Success() {
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(menuItem));
        doNothing().when(menuItemGateway).delete(menuItem);

        assertDoesNotThrow(() -> menuItemUseCase.delete(1L));

        verify(menuItemGateway).findById(1L);
        verify(menuItemGateway).delete(menuItem);
    }

    @Test
    @DisplayName("Não deve fazer nada ao tentar deletar um item de menu já inativo")
    void delete_AlreadyInactive() {
        menuItem.setActive(false);
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(menuItem));

        menuItemUseCase.delete(1L);

        verify(menuItemGateway).findById(1L);
        verify(menuItemGateway, never()).delete(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve habilitar um item de menu com sucesso")
    void enableById_Success() {
        menuItem.setActive(false);
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(menuItem));
        when(menuItemGateway.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        menuItemUseCase.enableById(1L, menuItemEnableDTO);

        assertTrue(menuItem.getActive());
        assertEquals(menuItemEnableDTO.getPrice(), menuItem.getPrice());
        verify(menuItemGateway).findById(1L);
        verify(menuItemGateway).save(menuItem);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar habilitar um item de menu inexistente")
    void enableById_NotFound() {
        when(menuItemGateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuItemUseCase.enableById(1L, menuItemEnableDTO));

        verify(menuItemGateway).findById(1L);
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }
}