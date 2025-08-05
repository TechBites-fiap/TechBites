package br.com.techchallenge.techbites.infrastructure.controllers;

import br.com.techchallenge.techbites.application.useCases.AuthUseCase;
import br.com.techchallenge.techbites.infrastructure.dtos.ChangePasswordDTO;
import br.com.techchallenge.techbites.infrastructure.dtos.LoginRequestDTO;
import br.com.techchallenge.techbites.infrastructure.docs.AuthControllerDoc;
import br.com.techchallenge.techbites.infrastructure.persistence.UserJpaEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDoc {

    private final AuthUseCase useCase;

    public AuthController(AuthUseCase useCase, AuthDTOMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    private final AuthDTOMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<String> validateLogin(@Valid @RequestBody LoginRequestDTO loginRequest) {
        UserJpaEntity entity = mapper.toEntity(loginRequest);
        boolean isValid = useCase.validateLogin(entity);

        if (isValid) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        useCase.changePassword(changePasswordDTO);
        return ResponseEntity.noContent().build();
    }

}
