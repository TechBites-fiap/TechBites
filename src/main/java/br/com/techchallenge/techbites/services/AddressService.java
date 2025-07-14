package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.AddressRequestDTO;
import br.com.techchallenge.techbites.dtos.AddressResponseDTO;
import br.com.techchallenge.techbites.entities.Address;
import br.com.techchallenge.techbites.mappers.AddressMapper;
import br.com.techchallenge.techbites.repositories.AddressRepository;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository repository;
    private final AddressMapper mapper;

    AddressService(AddressRepository repository , AddressMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Address createAddress(AddressRequestDTO address) {
        Address entity = mapper.toEntity(address);
        entity.setActive(true);
        return repository.save(entity);
    }

    public List<AddressResponseDTO> findAllAddress(Boolean active) {
        List<Address> address;
        if (active == null) {
            address = repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }else {
            address = repository.findByActive(active,  Sort.by(Sort.Direction.ASC, "id"));
        }

        return mapper.toListDTO(address);

    }

    public Optional<AddressResponseDTO> findAddressById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address", "id", id.toString())
        )).map(this.mapper::toDTO);
    }

    public void deleteAddressById(Long id) {
        Address address = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address", "id", id.toString())
        );
        repository.delete(address);
    }

    public void enableAddressById(Long id) {
        Address address = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address", "id", id.toString())
        );
        address.setActive(true);
        address.setLastUpdatedAt(LocalDateTime.now());
        repository.save(address);

    }

    public AddressResponseDTO updateAddressById(Long id, AddressRequestDTO addressDto) {
        Address addressExist = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address", "id", id.toString())
        );
        mapper.updateEntity(addressExist, addressDto);
        return mapper.toDTO(repository.save(addressExist));
    }




}
