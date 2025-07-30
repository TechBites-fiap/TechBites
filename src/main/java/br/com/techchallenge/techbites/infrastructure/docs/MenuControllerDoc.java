package br.com.techchallenge.techbites.infrastructure.docs;

import br.com.techchallenge.techbites.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuDTO;
import br.com.techchallenge.techbites.dtos.MenuUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Menus", description = "Endpoints para gerenciamento de menus")
public interface MenuControllerDoc {

    @PostMapping
    @Operation(summary = "Criar novo menu", description = "Cria um novo menu com os dados fornecidos")
    public ResponseEntity<MenuDTO> create(@RequestBody @Valid MenuCreateDTO dto);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar menu por ID", description = "Retorna os dados de um menu específico")
    public ResponseEntity<MenuDTO> findById(@Parameter(description = "ID do menu a ser buscado") @PathVariable Long id);

    @GetMapping
    @Operation(summary = "Listar menus", description = """
    Retorna menus conforme o valor do parâmetro 'active':
    
    - `true`: apenas menus ativos
    - `false`: apenas menus inativos
    - omitido: todos os menus
    """)
    public ResponseEntity<List<MenuDTO>> getAll(
            @Parameter(
                    description = "Filtrar menus por status. Use `true` para ativos, `false` para inativos ou deixe em branco para todos.",
                    example = "true"
            )
            @RequestParam(required = false) Boolean active);

    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "Buscar menus por ID do restaurante", description = "Retorna todos os menus de um restaurante específico")
    public ResponseEntity<List<MenuDTO>> findByRestaurantId(@Parameter(description = "ID do restaurante para buscar os menus") @PathVariable Long restaurantId);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar menu", description = "Atualiza os dados de um menu existente")
    public ResponseEntity<MenuDTO> update(@Parameter(description = "ID do menu a ser atualizado") @PathVariable Long id, @RequestBody @Valid MenuUpdateDTO dto);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Desabilitar menu",
            description = "Realiza um soft delete no menu. O registro não é removido do banco de dados, apenas marcado como inativo (active = false)."
    )
    public ResponseEntity<Void> delete(@Parameter(description = "ID do menu a ser desabilitado", example = "1") @PathVariable Long id);

    @PatchMapping("/enable/{id}")
    @Operation(
            summary = "Habilitar menu",
            description = "Reativa um menu anteriormente desabilitado, marcando o campo 'active' como true."
    )
    public ResponseEntity<Void> enableById(@Parameter(description = "ID do menu a ser habilitado", example = "2") @PathVariable Long id);
}