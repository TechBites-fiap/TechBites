package br.com.techchallenge.techbites.application.useCases;

import br.com.techchallenge.techbites.application.gateways.AddressGateway;
import br.com.techchallenge.techbites.application.gateways.RestaurantGateway;
import br.com.techchallenge.techbites.application.gateways.UserGateway;
import br.com.techchallenge.techbites.domain.models.Restaurant;
import br.com.techchallenge.techbites.infrastructure.gateways.AddressEntityMapper;
import br.com.techchallenge.techbites.infrastructure.gateways.RestaurantEntityMapper;
import br.com.techchallenge.techbites.infrastructure.gateways.UserEntityMapper;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantUseCaseImp implements RestaurantUseCase {

    private final RestaurantGateway repository;
    private final RestaurantEntityMapper mapper;
    private final UserGateway userRepository;
    private final UserEntityMapper ownerMapper;
    private final AddressGateway addressRepository;
    private final AddressEntityMapper addressMapper;

    public RestaurantUseCaseImp(RestaurantGateway restaurantRepository, RestaurantEntityMapper mapper, UserGateway userRepository, UserEntityMapper ownerMapper , AddressGateway addressRepository , AddressEntityMapper addressMapper) {
        this.repository = restaurantRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.ownerMapper = ownerMapper;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    public RestaurantJpaEntity createRestaurant(RestaurantJpaEntity entity) {
        Optional<UserJpaEntity> owner = userRepository.findUserById(entity.getOwner().getId());
        if (ownerMapper.toDomain(owner.get()).isInactive())
            throw new ResourceNotFoundException("User", "id", owner.get().getId().toString());
        entity.setOwner(owner.get());

        Restaurant domain = mapper.toDomain(entity);
        domain.validateOwnerRestaurantRole("CREATE");

        LocalDateTime now = LocalDateTime.now();

        domain.getAddress().setCreatedAt(now);
        domain.getAddress().setLastUpdatedAt(now);

        domain.getAddress().setActive(true);

        AddressJpaEntity addressSaved = addressRepository.createAddress(addressMapper.toEntity(domain.getAddress()));

        domain.setAddress(addressMapper.toDomain(addressSaved));

        domain.setCreatedAt(now);
        domain.setLastUpdatedAt(now);
        domain.setActive(true);

        return repository.createRestaurant(mapper.toEntity(domain));

    }

    @Override
    public List<RestaurantJpaEntity> getAllRestaurants(Boolean active) {
        return repository.getAllRestaurants(active);
    }

    @Override
    public Optional<RestaurantJpaEntity> findRestaurantById(Long id) {
        return repository.findRestaurantById(id);
    }

    @Override
    public void deleteRestaurantById(Long id) {
        Optional<RestaurantJpaEntity> entity = repository.findRestaurantById(id);
        addressRepository.deleteAddressById(entity.get().getAddress().getId());
        repository.deleteRestaurantById(id);
    }

    @Override
    public void enableRestaurantById(Long id) {
        Optional<RestaurantJpaEntity> entity = repository.findRestaurantById(id);

        LocalDateTime now = LocalDateTime.now();
        entity.get().setActive(true);
        entity.get().setLastUpdatedAt(now);

        entity.get().getAddress().setActive(true);
        entity.get().getAddress().setLastUpdatedAt(now);

        addressRepository.enableAddress(entity.get().getAddress());
        repository.enableRestaurant(entity.get());
    }

    @Override
    public RestaurantJpaEntity updateRestaurantById(Long id, RestaurantJpaEntity entity) {
        Optional<RestaurantJpaEntity> entityFound = repository.findRestaurantById(id);

        UserJpaEntity newOwner = userRepository.findUserById(entity.getOwner().getId()).get();
        entity.setOwner(newOwner);

        Restaurant domain = mapper.toDomain(entityFound.get());

        domain.validateOwnerRestaurantRole("UPDATE");

        if (domain.isInactive()) throw new ResourceNotFoundException("Restaurant", "id", entityFound.get().getId().toString());

        LocalDateTime now = LocalDateTime.now();

        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setCuisine(entity.getCuisine());
        domain.setOpeningHour(entity.getOpeningHour());
        domain.setActive(true);
        domain.setLastUpdatedAt(now);

        domain.getAddress().setStreet(entity.getAddress().getStreet());
        domain.getAddress().setNumber(entity.getAddress().getNumber());
        domain.getAddress().setComplement(entity.getAddress().getComplement());
        domain.getAddress().setNeighborhood(entity.getAddress().getNeighborhood());
        domain.getAddress().setCity(entity.getAddress().getCity());
        domain.getAddress().setState(entity.getAddress().getState());
        domain.getAddress().setZipCode(entity.getAddress().getZipCode());
        domain.getAddress().setActive(true);

        domain.getAddress().setLastUpdatedAt(now);

        RestaurantJpaEntity toUpdate = mapper.toEntity(domain);

        addressRepository.updateAddress(toUpdate.getAddress());
        return repository.updateAddress(toUpdate);

    }


}
