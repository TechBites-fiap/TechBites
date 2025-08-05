package br.com.techchallenge.techbites.infrastructure.gateways;

import br.com.techchallenge.techbites.application.gateways.AddressGateway;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressRepository;
import br.com.techchallenge.techbites.application.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AddressRepositoryGateway implements AddressGateway {

    private final AddressRepository repository;

    AddressRepositoryGateway(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AddressJpaEntity> findAddressById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address", "id", id.toString())
        ));
    }

    @Override
    public AddressJpaEntity updateAddress(AddressJpaEntity entityUpdate) {
        return repository.save(entityUpdate);
    }

    @Override
    public AddressJpaEntity createAddress(AddressJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteAddressById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void enableAddress(AddressJpaEntity address) {
        repository.save(address);
    }


}
