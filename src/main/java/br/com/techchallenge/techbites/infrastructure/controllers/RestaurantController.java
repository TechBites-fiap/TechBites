package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.RestaurantUseCase;
import br.com.techchallenge.techbites.infrastructure.dtos.RestaurantWithAddressRequestDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.RestaurantWithAddressResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.RestaurantJpaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantUseCase useCase;
    private RestaurantDTOMapper mapper;

    public RestaurantController(RestaurantUseCase useCase, RestaurantDTOMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<RestaurantWithAddressResponseDTO> createRestaurant(@RequestBody RestaurantWithAddressRequestDTO dto) {
        RestaurantJpaEntity entity = mapper.toJpaEntity(dto);
        RestaurantJpaEntity created = useCase.createRestaurant(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(created));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantWithAddressResponseDTO>> getAllRestaurants(@RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(mapper.toListResponseDTO(useCase.getAllRestaurants(active)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<RestaurantWithAddressResponseDTO>> findRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toOpJpaEntity(useCase.findRestaurantById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable Long id) {
        useCase.deleteRestaurantById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<Void> enableRestaurantById(@PathVariable Long id) {
        useCase.enableRestaurantById(id);
        return ResponseEntity.noContent().build() ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantWithAddressResponseDTO> updateTypeById(
            @PathVariable Long id,
            @RequestBody RestaurantWithAddressRequestDTO requestDTO) {
        RestaurantJpaEntity entity = mapper.toJpaEntity(requestDTO);
        RestaurantWithAddressResponseDTO response = mapper.toResponseDTO(useCase.updateRestaurantById(id, entity));
        return ResponseEntity.ok().body(response);
    }


}
