package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.MenuItemUseCase;
import br.com.techchallenge.techbites.dtos.MenuItemCreateDTO;
import br.com.techchallenge.techbites.dtos.MenuItemDTO;
import br.com.techchallenge.techbites.dtos.MenuItemEnableDTO; // Import adicionado
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemUseCase menuItemUseCase;
    private final MenuItemDTOMapper menuItemMapper;

    public MenuItemController(MenuItemUseCase menuItemUseCase, MenuItemDTOMapper menuItemMapper) {
        this.menuItemUseCase = menuItemUseCase;
        this.menuItemMapper = menuItemMapper;
    }

    @PostMapping
    public ResponseEntity<MenuItemDTO> create(@RequestBody @Valid MenuItemCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemMapper.toResponseDTO(menuItemUseCase.create(dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemMapper.toResponseDTO(menuItemUseCase.findById(id)));
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<MenuItemDTO>> findByMenuId(@PathVariable Long menuId) {
        List<MenuItemDTO> list = menuItemUseCase.findByMenuId(menuId).stream()
                .map(menuItemMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuItemUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<Void> enableById(@PathVariable Long id, @RequestBody @Valid MenuItemEnableDTO dto) {
        menuItemUseCase.enableById(id, dto);
        return ResponseEntity.noContent().build();
    }
}