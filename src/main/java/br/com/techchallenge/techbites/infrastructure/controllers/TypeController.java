package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.TypeUseCase;
import br.com.techchallenge.techbites.infrastructure.docs.TypeControllerDoc;
import br.com.techchallenge.techbites.domain.models.Type;
import br.com.techchallenge.techbites.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.dtos.TypeResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypeController implements TypeControllerDoc {

    private final TypeUseCase useCase;
    private final TypeDTOMapper mapper;

    public TypeController(
            TypeUseCase useCase,
            TypeDTOMapper mapper
    ) {
        this.useCase = useCase;
        this.mapper = mapper;
    }


    @Override
    @PostMapping
    public ResponseEntity<TypeResponseDTO> createType(@RequestBody @Valid TypeRequestDTO dto) {
        TypeJpaEntity created = useCase.createType(mapper.toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TypeResponseDTO>> getAllTypes(@RequestParam(required = false) Boolean active) {
        List<Type> types = useCase.findAllType(active);
        return ResponseEntity.ok(mapper.toListDTO(types));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TypeResponseDTO> findTypeById(@PathVariable Long id) {
        return useCase.findTypeById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TypeResponseDTO> updateTypeById(@PathVariable Long id, @RequestBody @Valid TypeRequestDTO dto) {
        Type updated = useCase.updateTypeById(id, mapper.toDomain(dto));
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeById(@PathVariable Long id) {
        useCase.deleteTypeById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/enable/{id}")
    public ResponseEntity<Void> enableTypeById(@PathVariable Long id) {
        useCase.enableTypeById(id);
        return ResponseEntity.noContent().build();
    }

}
