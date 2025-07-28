package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.AddressUseCase;
import br.com.techchallenge.techbites.domain.models.Address;
import br.com.techchallenge.techbites.dtos.AddressRequestDTO;
import br.com.techchallenge.techbites.dtos.AddressResponseDTO;
import br.com.techchallenge.techbites.infrastructure.docs.AddressControllerDoc;
import br.com.techchallenge.techbites.infrastructure.persistence.AddressJpaEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController implements AddressControllerDoc {

    private final AddressUseCase useCase;
    private final AddressDTOMapper mapper;


    public AddressController(AddressUseCase useCase, AddressDTOMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddressById(
            @PathVariable Long id,
            @RequestBody AddressRequestDTO addressRequest) {

        Address domain = mapper.toDomain(addressRequest);
        Address domainUpdate = useCase.updateAddressById(domain, id);
        AddressResponseDTO response = mapper.toResponseDTO(domainUpdate);
        return ResponseEntity.ok(response);
    }

}
