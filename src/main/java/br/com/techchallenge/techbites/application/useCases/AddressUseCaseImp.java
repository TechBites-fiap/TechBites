package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.AddressGateway;
import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.infrastructure.gateways.AddressEntityMapper;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AddressUseCaseImp implements AddressUseCase {

    private AddressGateway repository;
    private AddressEntityMapper mapper;

    public AddressUseCaseImp( AddressGateway repository, AddressEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Address updateAddressById(Address address, Long id) {
        Optional<AddressJpaEntity> found = repository.findAddressById(id);
        Address domain = mapper.toDomain(found.get());
        if (domain.isInactive()) throw new ResourceNotFoundException("Address", "id", id.toString());

        domain.setStreet(address.getStreet());
        domain.setNumber(address.getNumber());
        domain.setComplement(address.getComplement());
        domain.setNeighborhood(address.getNeighborhood());
        domain.setCity(address.getCity());
        domain.setState(address.getState());
        domain.setZipCode(address.getZipCode());
        domain.setLastUpdatedAt(LocalDateTime.now());

        AddressJpaEntity entityUpdate = mapper.toEntity(domain);

        Address domainUpdate = mapper.toDomain(repository.updateAddress(entityUpdate));

        return domainUpdate;


    }
}
