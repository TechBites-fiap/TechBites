package br.com.techchallenge.techbites.infrastructure.docs;

import br.com.techchallenge.techbites.dtos.AddressRequestDTO;
import br.com.techchallenge.techbites.dtos.AddressResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Address", description = "Endpoints relacionados a endereço")
public interface AddressControllerDoc {

    @Operation(summary = "Atualiza um endereço por ID", description = "Atualiza os dados de um endereço existente utilizando seu ID.")
    @PutMapping("/{id}")
    ResponseEntity<AddressResponseDTO>  updateAddressById(
            @Parameter(description = "ID do endereço a ser atualizado", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Dados para atualização do endereço")
            @RequestBody AddressRequestDTO addressRequest
    );
}
