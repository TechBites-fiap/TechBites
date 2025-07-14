package br.com.techchallenge.techbites.controllers.docs;

import br.com.techchallenge.techbites.dtos.AddressRequestDTO;
import br.com.techchallenge.techbites.dtos.AddressResponseDTO;
import br.com.techchallenge.techbites.entities.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Endereços", description = "Endpoints para gerenciamento de endereços")
public interface AddressControllerDoc {

    @PostMapping
    @Operation(summary = "Criar novo endereço", description = "Cria um novo endereço com os dados fornecidos")
    public ResponseEntity<Address> createAddress(
            @Valid @RequestBody AddressRequestDTO addressRequest);

    @GetMapping
    @Operation(summary = "Listar endereços", description = """
    Retorna endereços conforme o valor do parâmetro 'active':

    - `true`: apenas endereços ativos  
    - `false`: apenas endereços inativos  
    - omitido: todos os endereços
    """)
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses(
            @Parameter(
                    description = "Filtrar endereços por status. Use `true` para ativos, `false` para inativos ou deixe em branco para todos.",
                    example = "true"
            )
            @RequestParam(required = false) Boolean active);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por ID", description = "Retorna os dados de um endereço específico")
    public ResponseEntity<Optional<AddressResponseDTO>> findAddressById(
            @Parameter(description = "ID do endereço a ser buscado", example = "1") @PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço", description = "Atualiza os dados de um endereço existente")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @Parameter(description = "ID do endereço a ser atualizado", example = "1") @PathVariable Long id,
            @Valid @RequestBody AddressRequestDTO addressRequest);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Desabilitar endereço",
            description = "Realiza um soft delete no endereço. O registro não é removido do banco de dados, apenas marcado como inativo (active = false)."
    )
    public ResponseEntity<Void> deleteAddress(
            @Parameter(description = "ID do endereço a ser desabilitado", example = "1") @PathVariable Long id);

    @PatchMapping("/enable/{id}")
    @Operation(
            summary = "Habilitar endereço",
            description = "Reativa um endereço anteriormente desabilitado, marcando o campo 'active' como true."
    )
    public ResponseEntity<Void> enableAddressById(
            @Parameter(description = "ID do endereço a ser habilitado", example = "2") @PathVariable Long id);
}

