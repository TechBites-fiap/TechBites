package br.com.techchallenge.techbites.controllers;

import br.com.techchallenge.techbites.dtos.RestaurantWithAddressRequestDTO;
import br.com.techchallenge.techbites.dtos.RestaurantWithAddressResponseDTO;

import br.com.techchallenge.techbites.services.RestaurantSevice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantSevice service;

    public RestaurantController(RestaurantSevice service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RestaurantWithAddressResponseDTO> create(@RequestBody RestaurantWithAddressRequestDTO restaurantRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRestaurant(restaurantRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity <Optional<RestaurantWithAddressResponseDTO>> findRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findRestaurantById(id));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantWithAddressResponseDTO>> findAllRestaurants(@RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok().body(service.findAllRestaurants(active));
    }

}
