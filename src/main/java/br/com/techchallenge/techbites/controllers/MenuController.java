package br.com.techchallenge.techbites.controllers;

import br.com.techchallenge.techbites.dtos.MenuDTO;
import br.com.techchallenge.techbites.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuUpdateDTO;
import br.com.techchallenge.techbites.services.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * Busca um menu específico pelo seu ID.
     * O DTO retornado pode incluir a lista de seus itens filhos.
     * @param id O ID do menu.
     * @return 200 OK com os dados do menu, ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> findById(@PathVariable Long id) {
        MenuDTO dto = menuService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Busca todos os menus cadastrados.
     * @return 200 OK com a lista de menus.
     */
    @GetMapping
    public ResponseEntity<List<MenuDTO>> findAll() {
        List<MenuDTO> list = menuService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Busca todos os menus de um restaurante específico.
     * @param restaurantId O ID do restaurante.
     * @return 200 OK com a lista de menus do restaurante.
     */
    @GetMapping("/by-restaurant/{restaurantId}")
    public ResponseEntity<List<MenuDTO>> findAllByRestaurantId(@PathVariable Long restaurantId) {
        List<MenuDTO> list = menuService.findAllByRestaurantId(restaurantId);
        return ResponseEntity.ok(list);
    }

    /**
     * Cria um novo menu.
     * @param createDto DTO com os dados para a criação do novo menu.
     * @return 201 Created com a localização do novo recurso e seus dados.
     */
    @PostMapping
    public ResponseEntity<MenuDTO> create(@Valid @RequestBody MenuCreateDTO createDto) {
        MenuDTO createdDto = menuService.create(createDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getIdMenu())
                .toUri();
        return ResponseEntity.created(uri).body(createdDto);
    }

    /**
     * Atualiza um menu existente.
     * @param id O ID do menu a ser atualizado.
     * @param updateDto DTO com os novos dados para o menu.
     * @return 200 OK com os dados do menu atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> update(@PathVariable Long id, @Valid @RequestBody MenuUpdateDTO updateDto) {
        MenuDTO updatedDto = menuService.update(id, updateDto);
        return ResponseEntity.ok(updatedDto);
    }

    /**
     * Deleta um menu.
     * @param id O ID do menu a ser deletado.
     * @return 204 No Content em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}