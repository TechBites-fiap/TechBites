package br.com.techchallenge.techbites.infrastructure.docs;

import br.com.techchallenge.techbites.infrastructure.dtos.UserRequestDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.UserResponseDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.UserUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public interface UserControllerDoc {

    @PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest);

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
            @RequestParam(required = false) Boolean active);


    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
    public ResponseEntity<Optional<UserResponseDTO>> findUserById(
            @Parameter(description = "ID do usuário a ser buscado") @PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente (não altera a senha)")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "ID do usuário a ser atualizado") @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDTO userUpdateRequest);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Desabilitar usuário",
            description = "Realiza um soft delete no usuário. O registro não é removido do banco de dados, apenas marcado como inativo (active = false)."
    )
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário a ser desabilitado", example = "1") @PathVariable Long id);

    @PatchMapping("/enable/{id}")
    @Operation(
            summary = "Habilitar usuário",
            description = "Reativa um usuário anteriormente desabilitado, marcando o campo 'active' como true."
    )
    public ResponseEntity<Void> enableUserById(
            @Parameter(description = "ID do usuário a ser habilitado", example = "2") @PathVariable Long id);


}
