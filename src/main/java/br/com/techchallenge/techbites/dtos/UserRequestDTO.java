package br.com.techchallenge.techbites.dtos;

import br.com.techchallenge.techbites.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para requisições de criação de usuário.")
public record UserRequestDTO(

        @Schema(description = "Nome do usuário", example = "João da Silva")
        @NotBlank(message = "Name is required.")
        String name,

        @Schema(description = "Email do usuário", example = "joao.silva@gmail.com")
        @Email(message = "Email format is invalid.")
        @NotBlank(message = "Email is required.")
        String email,

        @Schema(description = "Senha do usuário. Mínimo de 6 caracteres.", example = "senha123")
        @Size(min = 6, message = "Password must be at least 6 characters.")
        @NotBlank(message = "Password is required.")
        String password,

        @Schema(description = "Papel do usuário no sistema. Valores permitidos: ADMIN, USER, USER_RESTAURANT", example = "USER")
        @NotNull(message = "Invalid role. Allowed values: ADMIN, USER, USER_RESTAURANT.")
        Role role
) {}
