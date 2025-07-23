package br.com.techchallenge.techbites.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class MenuItem {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String picturePath;
    private Menu menu;
    private Type itemType;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private Boolean active;

    public MenuItem() {}

    public MenuItem(Long id, String name, String description, BigDecimal price, String picturePath, Menu menu, Type itemType, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.picturePath = picturePath;
        this.menu = menu;
        this.itemType = itemType;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.active = active;
    }

    public boolean isInactive() {
        return !this.active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Type getItemType() {
        return itemType;
    }

    public void setItemType(Type itemType) {
        this.itemType = itemType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(id, menuItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}