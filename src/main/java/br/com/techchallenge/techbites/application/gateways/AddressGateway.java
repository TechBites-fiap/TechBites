package br.com.techchallenge.techbites.application.gateways;

import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;

import java.util.Optional;

public interface AddressGateway {

    public Optional<AddressJpaEntity> findAddressById(Long id);

    public AddressJpaEntity updateAddress(AddressJpaEntity entityUpdate);
}
