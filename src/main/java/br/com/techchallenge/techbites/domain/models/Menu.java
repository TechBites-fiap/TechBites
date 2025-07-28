package br.com.techchallenge.techbites.domain.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Menu {

    private Long id;
    private String name;
    private String description;
    private Restaurant restaurant;
    private List<MenuItem> items;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private Boolean active;

    public Menu() {}

    public Menu(Long id, String name, String description, Restaurant restaurant, List<MenuItem> items, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.restaurant = restaurant;
        this.items = items;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
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
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}