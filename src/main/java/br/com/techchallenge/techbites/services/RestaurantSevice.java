package br.com.techchallenge.techbites.services;

import br.com.techchallenge.techbites.dtos.RestaurantWithAddressRequestDTO;
import br.com.techchallenge.techbites.dtos.RestaurantWithAddressResponseDTO;
import br.com.techchallenge.techbites.entities.Address;
import br.com.techchallenge.techbites.entities.Restaurant;
import br.com.techchallenge.techbites.entities.User;
import br.com.techchallenge.techbites.mappers.AddressMapper;
import br.com.techchallenge.techbites.mappers.RestaurantMapper;
import br.com.techchallenge.techbites.mappers.UserMapper;
import br.com.techchallenge.techbites.repositories.AddressRepository;
import br.com.techchallenge.techbites.repositories.RestaurantRepository;
import br.com.techchallenge.techbites.repositories.UserRepository;
import br.com.techchallenge.techbites.services.exceptions.ResourceNotFoundException;
import br.com.techchallenge.techbites.services.exceptions.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantSevice {

    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private final RestaurantMapper restaurantMapper;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;

    public RestaurantSevice(
            RestaurantRepository restaurantRepository,
            AddressRepository addressRepository,
            UserRepository userRepository,
            RestaurantMapper restaurantMapper,
            AddressMapper addressMapper,
            UserMapper userMapper
    ) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.restaurantMapper = restaurantMapper;
        this.addressMapper = addressMapper;
        this.userMapper = userMapper;
    }

    public RestaurantWithAddressResponseDTO createRestaurant(RestaurantWithAddressRequestDTO restaurantDTO) {
        Long ownerId = restaurantDTO.ownerId();
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("id", ownerId.toString()));

        Restaurant entity = restaurantMapper.toEntity( restaurantDTO, owner);

        entity.getAddress().setActive(true);
        Address addres = addressRepository.save(entity.getAddress());

        entity.setAddress(addres);
        entity.setActive(true);

        return restaurantMapper.toDTO(restaurantRepository.save(entity));
    }

    public Optional<RestaurantWithAddressResponseDTO> findRestaurantById(Long id) {
        return Optional.of(restaurantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant", "id" , id.toString())
        )).map(this.restaurantMapper::toDTO);
    }

    public List<RestaurantWithAddressResponseDTO> findAllRestaurants(Boolean active) {
        List<Restaurant> restaurants;
        if (active == null) {
            restaurants = restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }else {
            restaurants = restaurantRepository.findByActive(active, Sort.by(Sort.Direction.ASC, "id"));
        }

        return restaurantMapper.toListDTO(restaurants);
    }
}
