package br.com.techchallenge.techbites.dtos;

import br.com.techchallenge.techbites.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO de resposta com os dados do usuário.")
public record UserResponseDTO(

        @Schema(description = "ID único do usuário", example = "1")
        Long id,

        @Schema(description = "Nome do usuário", example = "João da Silva")
        String name,

        @Schema(description = "Email do usuário", example = "joao.silva@gmail.com")
        String email,

        @Schema(description = "Papel do usuário no sistema", example = "USER")
        Role role,

        @Schema(description = "Data e hora de criação do usuário", example = "2024-05-05T14:30:00")
        LocalDateTime createdAt,

        @Schema(description = "Data e hora da última atualização", example = "2024-05-06T18:15:00")
        LocalDateTime lastUpdatedAt,

        @Schema(description = "Status ativo do usuário", example = "true")
        Boolean active

) {}
