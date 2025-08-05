package br.com.techchallenge.techbites.infrastructure.docs;

import br.com.techchallenge.techbites.infrastructure.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuItemDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuItemEnableDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Itens do Menu", description = "Endpoints para gerenciamento dos itens de um menu")
public interface MenuItemControllerDoc {

    @PostMapping
    @Operation(summary = "Criar novo item de menu", description = "Cria um novo item de menu com os dados fornecidos")
    public ResponseEntity<MenuItemDTO> create(@RequestBody @Valid MenuItemCreateDTO dto);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item de menu por ID", description = "Retorna os dados de um item de menu específico")
    public ResponseEntity<MenuItemDTO> findById(@Parameter(description = "ID do item de menu a ser buscado") @PathVariable Long id);

    @GetMapping("/menu/{menuId}")
    @Operation(summary = "Buscar itens de menu por ID do menu", description = "Retorna todos os itens de um menu específico")
    public ResponseEntity<List<MenuItemDTO>> findByMenuId(@Parameter(description = "ID do menu para buscar os itens") @PathVariable Long menuId);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Desabilitar item de menu",
            description = "Realiza um soft delete no item de menu. O registro não é removido do banco de dados, apenas marcado como inativo (active = false)."
    )
    public ResponseEntity<Void> delete(@Parameter(description = "ID do item de menu a ser desabilitado", example = "1") @PathVariable Long id);

    @PatchMapping("/enable/{id}")
    @Operation(
            summary = "Habilitar item de menu",
            description = "Reativa um item de menu anteriormente desabilitado, marcando o campo 'active' como true."
    )
    public ResponseEntity<Void> enableById(@Parameter(description = "ID do item de menu a ser habilitado", example = "2") @PathVariable Long id, @RequestBody @Valid MenuItemEnableDTO dto);
}