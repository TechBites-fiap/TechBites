package br.com.techchallenge.techbites.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class MenuItemCreateDTO {
    @NotBlank(message = "The name can not be empty.")
    private String name;
    private String description;
    @NotNull(message = "The price can not be empty.")
    @Positive(message = "The price must be positive.")
    private BigDecimal price;
    private String picturePath;
    @NotNull(message = "The ID menu can not be empty.")
    private Long menuId;
    @NotNull(message = "The itemType can not be empty.")
    private Long itemTypeId;

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
}