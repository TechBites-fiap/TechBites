package br.com.techchallenge.techbites.infrastructure.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class MenuDTO {
    private Long idMenu;
    private String name;
    private String description;
    private Long restaurantId;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private List<MenuItemDTO> items;

    // Getters e Setters
    public Long getIdMenu() { return idMenu; }
    public void setIdMenu(Long idMenu) { this.idMenu = idMenu; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
    public List<MenuItemDTO> getItems() { return items; }
    public void setItems(List<MenuItemDTO> items) { this.items = items; }
}