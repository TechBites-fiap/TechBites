package br.com.techchallenge.techbites.infrastructure.docs;

import br.com.techchallenge.techbites.dtos.ChangePasswordDTO;
import br.com.techchallenge.techbites.dtos.LoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autenticação", description = "Endpoints para login e gerenciamento de senha")
public interface AuthControllerDoc {

    @Operation(
            summary = "Autenticar usuário",
            description = "Realiza o login e retorna um token JWT"
    )
    ResponseEntity<String> validateLogin(@Valid @RequestBody LoginRequestDTO loginRequest);

    @Operation(
            summary = "Alterar a senha do usuário",
            description = "Realiza a troca de senha do usuário a partir do e-mail e senha atual"
    )
    ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO);

}
