package br.com.techchallenge.techbites.controllers;

import br.com.techchallenge.techbites.controllers.docs.AddressControllerDoc;
import br.com.techchallenge.techbites.dtos.AddressRequestDTO;
import br.com.techchallenge.techbites.dtos.AddressResponseDTO;
import br.com.techchallenge.techbites.entities.Address;
import br.com.techchallenge.techbites.services.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController implements AddressControllerDoc {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressRequestDTO addressRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAddress(addressRequest));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses(@RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(service.findAllAddress(active));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Optional<AddressResponseDTO>> findAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAddressById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable Long id,
            @RequestBody AddressRequestDTO addressRequest) {
        return ResponseEntity.ok(service.updateAddressById(id, addressRequest));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        service.deleteAddressById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/enable/{id}")
    public ResponseEntity<Void> enableAddressById(Long id) {
        service.enableAddressById(id);
        return ResponseEntity.noContent().build();
    }
}
