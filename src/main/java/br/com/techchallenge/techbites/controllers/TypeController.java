package br.com.techchallenge.techbites.controllers;

import br.com.techchallenge.techbites.controllers.docs.TypeControllerDoc;
import br.com.techchallenge.techbites.dtos.TypeRequestDTO;
import br.com.techchallenge.techbites.dtos.TypeResponseDTO;
import br.com.techchallenge.techbites.entities.Type;
import br.com.techchallenge.techbites.services.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/types")
public class TypeController  implements TypeControllerDoc {

    private final TypeService service;

    public TypeController(TypeService typeService) {
        this.service = typeService;
    }

    @Override
    public ResponseEntity<Type> createType(@RequestBody TypeRequestDTO typeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createType(typeRequest));
    }

    @Override
    public ResponseEntity<List<TypeResponseDTO>> getAllTypes(@RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(service.findAllType(active));
    }

    @Override
    public ResponseEntity<Optional<TypeResponseDTO>> findTypeById( @PathVariable Long id) {
        return ResponseEntity.ok(service.findTypeById(id));
    }

    @Override
    public ResponseEntity<TypeResponseDTO> updateTypeById(
            @PathVariable Long id,
            @RequestBody TypeRequestDTO typeRequest) {
        return ResponseEntity.ok(service.updateTypeById(id, typeRequest));
    }

    @Override
    public ResponseEntity<Void> deleteTypeById(@PathVariable Long id) {
        service.deleteTypeById(id);
        return ResponseEntity.noContent().build() ;
    }

    @Override
    public ResponseEntity<Void> enableTypeById(@PathVariable Long id) {
        service.enableTypeById(id);
        return ResponseEntity.noContent().build() ;
    }
}
