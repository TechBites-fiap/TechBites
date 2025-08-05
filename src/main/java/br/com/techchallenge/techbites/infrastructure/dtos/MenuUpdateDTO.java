package br.com.techchallenge.techbites.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;

public class MenuUpdateDTO {
    @NotBlank(message = "O nome do menu não pode ser vazio")
    private String name;
    private String description;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}