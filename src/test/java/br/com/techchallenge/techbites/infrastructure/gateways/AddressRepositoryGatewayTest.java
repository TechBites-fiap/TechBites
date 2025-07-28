package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressRepository;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AddressRepositoryGatewayTest {

    private AddressRepository repository;
    private AddressRepositoryGateway gateway;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(AddressRepository.class);
        gateway = new AddressRepositoryGateway(repository);
    }

    @Test
    @DisplayName("GIVEN address exists WHEN findAddressById THEN return address")
    void testFindAddressByIdSuccess() {
        AddressJpaEntity entity = new AddressJpaEntity();
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<AddressJpaEntity> result = gateway.findAddressById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("GIVEN address does not exist WHEN findAddressById THEN throw exception")
    void testFindAddressByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> gateway.findAddressById(1L));

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("GIVEN valid address WHEN updateAddress THEN return updated address")
    void testUpdateAddress() {
        AddressJpaEntity entity = new AddressJpaEntity();
        entity.setId(1L);

        when(repository.save(entity)).thenReturn(entity);

        AddressJpaEntity result = gateway.updateAddress(entity);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("GIVEN valid address WHEN createAddress THEN return created address")
    void testCreateAddress() {
        AddressJpaEntity entity = new AddressJpaEntity();
        entity.setId(2L);

        when(repository.save(entity)).thenReturn(entity);

        AddressJpaEntity result = gateway.createAddress(entity);

        assertThat(result.getId()).isEqualTo(2L);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("GIVEN valid ID WHEN deleteAddressById THEN repository should delete")
    void testDeleteAddressById() {
        doNothing().when(repository).deleteById(1L);

        gateway.deleteAddressById(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("GIVEN valid address WHEN enableAddress THEN repository should save")
    void testEnableAddress() {
        AddressJpaEntity entity = new AddressJpaEntity();
        entity.setId(3L);

        when(repository.save(entity)).thenReturn(entity);

        gateway.enableAddress(entity);

        verify(repository).save(entity);
    }
}
