package br.com.techchallenge.techbites.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MenuCreateDTO {
    @NotBlank(message = "O nome do menu não pode ser vazio")
    private String name;
    private String description;
    @NotNull(message = "O ID do restaurante é obrigatório")
    private Long restaurantId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
}