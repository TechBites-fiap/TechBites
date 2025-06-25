package br.com.techchallenge.techbites.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

public record ChangePasswordDTO(

        @NotBlank(message = "email is required.")
        @Email(message = "Email format is invalid.")
        String email ,


        @NotBlank(message = "currentPassword is required.")
        @Schema(description = "Senha atual do usuário. Mínimo de 6 caracteres.", example = "senha123")
        String currentPassword,

        @NotBlank(message = "newPassword is required.")
        @Schema(description = "Nova senha do usuário. Mínimo de 6 caracteres.", example = "senha123")
        @Size(min = 6, message = "Password must be at least 6 characters.")
        String newPassword,

        @NotBlank(message = "confirmNewPassword is required.")
        @Schema(description = "Senha de confirmação do usuário. Mínimo de 6 caracteres.", example = "senha123")
        @Size(min = 6, message = "Password must be at least 6 characters.")
        String confirmNewPassword
) {
}
