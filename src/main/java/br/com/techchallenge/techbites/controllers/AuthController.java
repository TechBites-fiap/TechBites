package br.com.techchallenge.techbites.controllers;

import br.com.techchallenge.techbites.dtos.ChangePasswordDTO;
import br.com.techchallenge.techbites.dtos.LoginRequestDTO;
import br.com.techchallenge.techbites.services.AuthService;
import br.com.techchallenge.techbites.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth") // Todas as rotas aqui começarão com /auth
@Tag(name = "Autenticação", description = "Endpoints para login e gerenciamento de senha")
public class AuthController {

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Autowired
    private AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Realiza o login e retorna um token JWT")
    public ResponseEntity<String> validateLogin(@Valid @RequestBody LoginRequestDTO loginRequest) {
        boolean isValid = authService.validateLogin(loginRequest);

        if (isValid) {
            return ResponseEntity.ok("Login successful");
        } else {
            // HTTP 401 Unauthorized é o status correto para credenciais inválidas.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
        }
    }

    @PostMapping("/change-password")
    @Operation(summary = "Alterar a senha do usuário", description = "Realiza a troca de senha do usuário a partir do e-mail e senha atual")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(changePasswordDTO);
        return ResponseEntity.noContent().build();
    }
}