package br.com.techchallenge.techbites.infrastructure.dtos;

import br.com.techchallenge.techbites.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para requisições de atualização de dados de um usuário (não inclui a senha).")
public record UserUpdateRequestDTO(

        @Schema(description = "Nome do usuário", example = "João da Silva")
        @NotBlank(message = "Name is required.")
        String name,

        @Schema(description = "Email do usuário", example = "joao.silva@gmail.com")
        @Email(message = "Email format is invalid.")
        @NotBlank(message = "Email is required.")
        String email,

        @Schema(description = "Papel do usuário no sistema. Valores permitidos: ADMIN, USER, USER_RESTAURANT", example = "USER")
        @NotNull(message = "Invalid role. Allowed values: ADMIN, USER, USER_RESTAURANT.")
        Role role
) {}