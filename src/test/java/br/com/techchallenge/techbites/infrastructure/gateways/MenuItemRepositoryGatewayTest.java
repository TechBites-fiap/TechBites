package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.models.MenuItem;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuItemJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MenuItemRepositoryGatewayTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuItemEntityMapper menuItemMapper;

    @InjectMocks
    private MenuItemRepositoryGateway menuItemRepositoryGateway;

    private MenuItem menuItemDomain;
    private MenuItemJpaEntity menuItemEntity;

    @BeforeEach
    void setUp() {
        menuItemDomain = new MenuItem();
        menuItemEntity = new MenuItemJpaEntity();
    }

    @Test
    @DisplayName("Deve salvar um item de menu")
    void save() {
        when(menuItemMapper.toEntity(any(MenuItem.class))).thenReturn(menuItemEntity);
        when(menuItemRepository.save(any(MenuItemJpaEntity.class))).thenReturn(menuItemEntity);
        when(menuItemMapper.toDomain(any(MenuItemJpaEntity.class))).thenReturn(menuItemDomain);

        MenuItem result = menuItemRepositoryGateway.save(menuItemDomain);

        assertNotNull(result);
        assertEquals(menuItemDomain, result);
        verify(menuItemMapper).toEntity(menuItemDomain);
        verify(menuItemRepository).save(menuItemEntity);
        verify(menuItemMapper).toDomain(menuItemEntity);
    }

    @Test
    @DisplayName("Deve encontrar um item de menu pelo ID")
    void findById() {
        when(menuItemRepository.findByIdInactive(1L)).thenReturn(Optional.of(menuItemEntity));
        when(menuItemMapper.toDomain(menuItemEntity)).thenReturn(menuItemDomain);

        Optional<MenuItem> result = menuItemRepositoryGateway.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(menuItemDomain, result.get());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio se o item de menu não for encontrado")
    void findById_NotFound() {
        when(menuItemRepository.findByIdInactive(1L)).thenReturn(Optional.empty());

        Optional<MenuItem> result = menuItemRepositoryGateway.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve encontrar itens de menu pelo ID do menu")
    void findByMenuId() {
        when(menuItemRepository.findByMenuId(1L)).thenReturn(Collections.singletonList(menuItemEntity));
        when(menuItemMapper.toDomain(menuItemEntity)).thenReturn(menuItemDomain);

        List<MenuItem> result = menuItemRepositoryGateway.findByMenuId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve deletar um item de menu")
    void deleteTest() {
        when(menuItemMapper.toEntity(menuItemDomain)).thenReturn(menuItemEntity);
        doNothing().when(menuItemRepository).delete(menuItemEntity);

        menuItemRepositoryGateway.delete(menuItemDomain);

        verify(menuItemMapper).toEntity(menuItemDomain);
        verify(menuItemRepository).delete(menuItemEntity);
    }
}