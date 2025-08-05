package br.com.techchallenge.techbites.infrastructure.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MenuItemDTO {
    private Long idMenuItem;
    private String name;
    private String description;
    private BigDecimal price;
    private String picturePath;
    private Long menuId;
    private Long itemTypeId;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public Long getIdMenuItem() { return idMenuItem; }
    public void setIdMenuItem(Long idMenuItem) { this.idMenuItem = idMenuItem; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getPicturePath() { return picturePath; }
    public void setPicturePath(String picturePath) { this.picturePath = picturePath; }
    public Long getMenuId() { return menuId; }
    public void setMenuId(Long menuId) { this.menuId = menuId; }
    public Long getItemTypeId() { return itemTypeId; }
    public void setItemTypeId(Long itemTypeId) { this.itemTypeId = itemTypeId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
}