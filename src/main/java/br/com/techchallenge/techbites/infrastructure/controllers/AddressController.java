package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.AddressUseCase;
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
        AddressJpaEntity entity = mapper.toJpaEntity(addressRequest);
        AddressResponseDTO response = mapper.toResponseDTO(useCase.updateAddressById(entity, id));
        return ResponseEntity.ok(response);
    }

}
