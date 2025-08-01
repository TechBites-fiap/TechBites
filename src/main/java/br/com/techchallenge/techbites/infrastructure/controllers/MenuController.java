package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.MenuUseCase;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuCreateDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.MenuUpdateDTO;
import br.com.techchallenge.techbites.infrastructure.docs.MenuControllerDoc;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menus")
public class MenuController implements MenuControllerDoc {

    private final MenuUseCase menuUseCase;
    private final MenuDTOMapper menuMapper;

    public MenuController(MenuUseCase menuUseCase, MenuDTOMapper menuMapper) {
        this.menuUseCase = menuUseCase;
        this.menuMapper = menuMapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<MenuDTO> create(@RequestBody @Valid MenuCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuMapper.toResponseDTO(menuUseCase.create(dto)));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(menuMapper.toResponseDTO(menuUseCase.findById(id)));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAll(@RequestParam(required = false) Boolean active) {
        List<MenuDTO> list = menuUseCase.getAll(active).stream()
                .map(menuMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuDTO>> findByRestaurantId(@PathVariable Long restaurantId) {
        List<MenuDTO> list = menuUseCase.findByRestaurantId(restaurantId).stream()
                .map(menuMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> update(@PathVariable Long id, @RequestBody @Valid MenuUpdateDTO dto) {
        return ResponseEntity.ok(menuMapper.toResponseDTO(menuUseCase.update(id, dto)));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/enable/{id}")
    public ResponseEntity<Void> enableById(@PathVariable Long id) {
        menuUseCase.enableById(id);
        return ResponseEntity.noContent().build();
    }
}