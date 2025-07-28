package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.AddressGateway;
import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.infrastructure.gateways.AddressEntityMapper;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class AddressUseCaseImpTest {

    private AddressGateway gateway;
    private AddressEntityMapper mapper;
    private AddressUseCaseImp useCase;

    @BeforeEach
    void setUp() {
        gateway = mock(AddressGateway.class);
        mapper = mock(AddressEntityMapper.class);
        useCase = new AddressUseCaseImp(gateway, mapper);
    }

    @Test
    @DisplayName("GIVEN a valid Address WHEN updateAddressById is called THEN it should update and return the domain object")
    void testUpdateAddressSuccess() {
        // GIVEN
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();

        AddressJpaEntity existingEntity = new AddressJpaEntity(); // pode mockar mais se quiser
        Address domainFound = new Address(id, "Old St", "123", "", "Old Hood", "Old City", "OS", "00000-000", now, now, true);
        Address domainInput = new Address(null, "New St", "456", "Apt 9", "New Hood", "New City", "NS", "11111-111", null, null, true);
        Address updatedDomain = new Address(id, "New St", "456", "Apt 9", "New Hood", "New City", "NS", "11111-111", now, now, true);
        AddressJpaEntity updatedEntity = new AddressJpaEntity(); // mesma coisa, pode mockar com setters

        when(gateway.findAddressById(id)).thenReturn(Optional.of(existingEntity));
        when(mapper.toDomain(existingEntity)).thenReturn(domainFound);
        when(mapper.toEntity(any(Address.class))).thenReturn(updatedEntity);
        when(gateway.updateAddress(updatedEntity)).thenReturn(updatedEntity);
        when(mapper.toDomain(updatedEntity)).thenReturn(updatedDomain);

        // WHEN
        Address result = useCase.updateAddressById(domainInput, id);

        // THEN
        assertNotNull(result);
        assertEquals("New St", result.getStreet());
        assertEquals("456", result.getNumber());
        assertEquals("Apt 9", result.getComplement());
        verify(gateway).updateAddress(any(AddressJpaEntity.class));
    }

    @Test
    @DisplayName("GIVEN an inactive Address WHEN updateAddressById is called THEN it should throw ResourceNotFoundException")
    void testUpdateInactiveAddressThrowsException() {
        // GIVEN
        Long id = 1L;
        AddressJpaEntity existingEntity = new AddressJpaEntity();
        Address inactiveDomain = new Address(id, "Rua", "10", null, null, null, null, null, null, null, false);
        Address domainInput = new Address(null, "Rua Nova", "20", null, null, null, null, null, null, null, true);

        when(gateway.findAddressById(id)).thenReturn(Optional.of(existingEntity));
        when(mapper.toDomain(existingEntity)).thenReturn(inactiveDomain);

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            useCase.updateAddressById(domainInput, id);
        });

        verify(gateway, never()).updateAddress(any());
    }
}


