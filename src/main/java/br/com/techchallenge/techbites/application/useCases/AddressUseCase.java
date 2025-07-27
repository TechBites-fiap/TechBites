package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;

public interface AddressUseCase {

    public Address updateAddressById(Address address, Long id);

}
