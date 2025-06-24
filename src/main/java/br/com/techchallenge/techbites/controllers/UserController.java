package br.com.techchallenge.techbites.controllers;

import br.com.techchallenge.techbites.DTOs.ChangePasswordDTO;
import br.com.techchallenge.techbites.DTOs.UserRequestDTO;
import br.com.techchallenge.techbites.DTOs.UserResponseDTO;
import br.com.techchallenge.techbites.services.UserService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(userRequest));
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = """
    Retorna usuários conforme o valor do parâmetro 'active':
    
    - `true`: apenas usuários ativos  
    - `false`: apenas usuários inativos  
    - omitido: todos os usuários
    """)
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @Parameter(
                    description = "Filtrar usuários por status. Use `true` para ativos, `false` para inativos ou deixe em branco para todos.",
                    example = "true"
            )
            @RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(service.findAllUsers(active));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
    public ResponseEntity<Optional<UserResponseDTO>> findUserById(
            @Parameter(description = "ID do usuário a ser buscado") @PathVariable Long id) {
        return ResponseEntity.ok(service.findUserById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Valid
            @Parameter(description = "ID do usuário a ser atualizado") @PathVariable Long id,
            @RequestBody UserRequestDTO userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateUserById(id, userRequest));

    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Desabilitar usuário",
            description = "Realiza um soft delete no usuário. O registro não é removido do banco de dados, apenas marcado como inativo (active = false)."
    )
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário a ser desabilitado", example = "1") @PathVariable Long id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/enable/{id}")
    @Operation(
            summary = "Habilitar usuário",
            description = "Reativa um usuário anteriormente desabilitado, marcando o campo 'active' como true."
    )
    public ResponseEntity<Void> enableUserById(
            @Parameter(description = "ID do usuário a ser habilitado", example = "2") @PathVariable Long id) {
        service.enableUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/auth/password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        service.changePassword(changePasswordDTO);
        return ResponseEntity.noContent().build();
    }

}