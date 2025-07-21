package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;

public interface AddressUseCase {

    public AddressJpaEntity updateAddressById(AddressJpaEntity address, Long id);

}
