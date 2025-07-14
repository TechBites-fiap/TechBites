package br.com.techchallenge.techbites.controllers;

import br.com.techchallenge.techbites.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuItemDTO;
import br.com.techchallenge.techbites.services.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("menu/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    /**
     * Endpoint para buscar um item de menu pelo seu ID.
     * Exemplo de como seu handler funciona:
     * - Se o item for encontrado, retorna 200 OK com o item.
     * - Se o item NÃO for encontrado, o service lança ResourceNotFoundException,
     * e seu handler o captura e retorna um 404 NOT FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> findById(@PathVariable Long id) {
        MenuItemDTO dto = menuItemService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para buscar todos os itens de um menu específico.
     */
    @GetMapping("/by-menu/{menuId}")
    public ResponseEntity<List<MenuItemDTO>> findAllByMenuId(@PathVariable Long menuId) {
        List<MenuItemDTO> list = menuItemService.findAllByMenuId(menuId);
        return ResponseEntity.ok(list);
    }

    /**
     * Endpoint para criar um novo item de menu.
     * Exemplo de como seu handler de validação funciona:
     * - Se o DTO for válido, cria o item e retorna 201 CREATED.
     * - Se um campo como "name" estiver em branco no JSON, a anotação @Valid
     * lança MethodArgumentNotValidException, e seu handler o captura,
     * retornando um 400 BAD REQUEST com os detalhes do erro.
     */
    @PostMapping
    public ResponseEntity<MenuItemDTO> create(@Valid @RequestBody MenuItemCreateDTO createDto) {
        MenuItemDTO createdDto = menuItemService.create(createDto);

        // Cria a URI para o novo recurso criado, uma boa prática REST
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getIdMenuItem())
                .toUri();

        return ResponseEntity.created(uri).body(createdDto);
    }

    /**
     * Endpoint para deletar um item de menu.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuItemService.delete(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}