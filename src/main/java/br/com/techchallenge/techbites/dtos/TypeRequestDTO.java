package br.com.techchallenge.techbites.dtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record TypeRequestDTO(@NotBlank(message = "Type is required.") String type) {

}
