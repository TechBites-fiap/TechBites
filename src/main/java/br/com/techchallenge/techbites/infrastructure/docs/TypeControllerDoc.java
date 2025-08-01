package br.com.techchallenge.techbites.infrastructure.docs;

import br.com.techchallenge.techbites.infrastructure.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.TypeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Tipos", description = "Endpoints para gerenciamento de tipos")
public interface TypeControllerDoc {

    @PostMapping
    @Operation(summary = "Criar tipo", description = "Cria um novo tipo e retorna os dados criados.")
    ResponseEntity<TypeResponseDTO> createType(@RequestBody @Valid TypeRequestDTO dto);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo por ID", description = "Retorna os dados de um tipo específico, se estiver ativo.")
    ResponseEntity<Optional<TypeResponseDTO>> findTypeById(
            @Parameter(description = "ID do tipo a ser buscado", example = "1")
            @PathVariable Long id);

    @GetMapping
    @Operation(summary = "Listar tipos", description = """
    Retorna uma lista de tipos conforme o valor do parâmetro 'active':
    
    - `true`: apenas tipos ativos  
    - `false`: apenas tipos inativos  
    - omitido: todos os tipos
    """)
    ResponseEntity<List<TypeResponseDTO>> getAllTypes(
            @Parameter(
                    description = "Filtrar tipos por status. Use `true` para ativos, `false` para inativos ou deixe em branco para todos.",
                    example = "true"
            )
            @RequestParam(required = false) Boolean active);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo", description = "Atualiza os dados de um tipo existente.")
    ResponseEntity<TypeResponseDTO> updateTypeById(
            @Parameter(description = "ID do tipo a ser atualizado", example = "1")
            @PathVariable Long id,
            @RequestBody @Valid TypeRequestDTO typeRequest);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Desabilitar tipo",
            description = "Realiza um soft delete no tipo. O registro não é removido do banco de dados, apenas marcado como inativo (`active = false`)."
    )
    ResponseEntity<Void> deleteTypeById(
            @Parameter(description = "ID do tipo a ser desabilitado", example = "1")
            @PathVariable Long id);

    @PatchMapping("/enable/{id}")
    @Operation(
            summary = "Habilitar tipo",
            description = "Reativa um tipo anteriormente desabilitado, marcando o campo 'active' como `true`."
    )
    ResponseEntity<Void> enableTypeById(
            @Parameter(description = "ID do tipo a ser habilitado", example = "2")
            @PathVariable Long id);
}
