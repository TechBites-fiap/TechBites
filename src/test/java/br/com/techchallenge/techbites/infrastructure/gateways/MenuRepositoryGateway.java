package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.domain.models.Menu;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MenuRepositoryGatewayTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuEntityMapper menuMapper;

    @InjectMocks
    private MenuRepositoryGateway menuRepositoryGateway;

    private Menu menuDomain;
    private MenuJpaEntity menuEntity;

    @BeforeEach
    void setUp() {
        menuDomain = new Menu();
        menuEntity = new MenuJpaEntity();
    }

    @Test
    @DisplayName("Deve salvar um menu com sucesso")
    void save() {
        when(menuMapper.toEntity(any(Menu.class))).thenReturn(menuEntity);
        when(menuRepository.save(any(MenuJpaEntity.class))).thenReturn(menuEntity);
        when(menuMapper.toDomain(any(MenuJpaEntity.class))).thenReturn(menuDomain);

        Menu result = menuRepositoryGateway.save(menuDomain);

        assertNotNull(result);
        assertEquals(menuDomain, result);
        verify(menuMapper).toEntity(menuDomain);
        verify(menuRepository).save(menuEntity);
        verify(menuMapper).toDomain(menuEntity);
    }

    @Test
    @DisplayName("Deve encontrar um menu pelo ID")
    void findById() {
        when(menuRepository.findByIdInactive(1L)).thenReturn(Optional.of(menuEntity));
        when(menuMapper.toDomain(menuEntity)).thenReturn(menuDomain);

        Optional<Menu> result = menuRepositoryGateway.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(menuDomain, result.get());
        verify(menuRepository).findByIdInactive(1L);
    }

    @Test
    @DisplayName("Deve encontrar menus pelo ID do restaurante")
    void findByRestaurantId() {
        when(menuRepository.findByRestaurantId(1L)).thenReturn(Collections.singletonList(menuEntity));
        when(menuMapper.toDomain(menuEntity)).thenReturn(menuDomain);

        List<Menu> result = menuRepositoryGateway.findByRestaurantId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(menuRepository).findByRestaurantId(1L);
    }

    @Test
    @DisplayName("Deve encontrar todos os menus quando 'active' for nulo")
    void findAll_whenActiveIsNull() {
        when(menuRepository.findAll(any(Sort.class))).thenReturn(Collections.singletonList(menuEntity));
        when(menuMapper.toDomain(menuEntity)).thenReturn(menuDomain);

        List<Menu> result = menuRepositoryGateway.findAll(null);

        assertFalse(result.isEmpty());
        verify(menuRepository).findAll(any(Sort.class));
        verify(menuRepository, never()).findByActive(anyBoolean(), any(Sort.class));
    }

    @Test
    @DisplayName("Deve encontrar menus por status 'active'")
    void findAll_whenActiveIsNotNull() {
        when(menuRepository.findByActive(eq(true), any(Sort.class))).thenReturn(Collections.singletonList(menuEntity));
        when(menuMapper.toDomain(menuEntity)).thenReturn(menuDomain);

        List<Menu> result = menuRepositoryGateway.findAll(true);

        assertFalse(result.isEmpty());
        verify(menuRepository).findByActive(eq(true), any(Sort.class));
        verify(menuRepository, never()).findAll(any(Sort.class));
    }

    @Test
    @DisplayName("Deve deletar um menu")
    void deleteTest() {
        when(menuMapper.toEntity(menuDomain)).thenReturn(menuEntity);
        doNothing().when(menuRepository).delete(menuEntity);

        menuRepositoryGateway.delete(menuDomain);

        verify(menuMapper).toEntity(menuDomain);
        verify(menuRepository).delete(menuEntity);
    }
}