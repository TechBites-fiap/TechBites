package br.com.techchallenge.techbites.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "The email is mandatory.")
        @Email(message = "Invalid email format.")
        String email,

        @NotBlank(message = "The password is mandatory.")
        String password
) {}