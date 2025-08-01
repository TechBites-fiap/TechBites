package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.TypeUseCase;
import br.com.techchallenge.techbites.infrastructure.docs.TypeControllerDoc;
import br.com.techchallenge.techbites.infrastructure.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.TypeResponseDTO;
import br.com.techchallenge.techbites.infrastructure.persistence.TypeJpaEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<TypeResponseDTO> createType(@RequestBody @Valid TypeRequestDTO dto) {
        TypeJpaEntity created = useCase.createType(mapper.toJpaEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TypeResponseDTO>> findTypeById(@PathVariable Long id) {
        Optional<TypeJpaEntity> found = useCase.findTypeById(id);
        return ResponseEntity.ok().body(mapper.toOpResponseDTO(found));
    }

    @GetMapping
    public ResponseEntity<List<TypeResponseDTO>> getAllTypes(@RequestParam(required = false) Boolean active) {
        List<TypeJpaEntity> entities = useCase.getAllTypes(active);
        return ResponseEntity.ok(mapper.toListResponseDTO(entities));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeById(@PathVariable Long id) {
        useCase.deleteTypeById(id);
        return ResponseEntity.noContent().build() ;
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<Void> enableTypeById(@PathVariable Long id) {
        useCase.enableTypeById(id);
        return ResponseEntity.noContent().build() ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeResponseDTO> updateTypeById(
            @PathVariable Long id,
            @RequestBody TypeRequestDTO typeRequest) {
        TypeJpaEntity entity = mapper.toJpaEntity(typeRequest);
        TypeResponseDTO response = mapper.toResponseDTO(useCase.updateTypeById(id, entity));
        return ResponseEntity.ok().body(response);
    }


}
