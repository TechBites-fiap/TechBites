package br.com.techchallenge.techbites.domain.models;

import br.com.techchallenge.techbites.domain.enums.Role;
import br.com.techchallenge.techbites.services.exceptions.UnauthorizedException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Restaurant {

    private Long id;
    private String name;
    private String description;
    private Address address;
    private String cuisine;
    private String openingHour;
    private User owner;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private boolean active;

    public Restaurant() {}

    public Restaurant(Long id, String name, String description, Address address, String cuisine, String openingHour, User owner, LocalDateTime createdAt, LocalDateTime lastUpdatedAt, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.cuisine = cuisine;
        this.openingHour = openingHour;
        this.owner = owner;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.active = active;
    }

    public boolean isInactive() {
        return !this.active;
    }

    public void validateOwnerRestaurantRole(String action) {
        if (owner == null || !this.getOwner().getRole().equals(Role.USER_RESTAURANT)) {
            assert owner != null;
            throw new UnauthorizedException(action, "Restaurant", owner.getEmail());
        }
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return this.active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return active == that.active && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(address, that.address) && Objects.equals(cuisine, that.cuisine) && Objects.equals(openingHour, that.openingHour) && Objects.equals(owner, that.owner) && Objects.equals(createdAt, that.createdAt) && Objects.equals(lastUpdatedAt, that.lastUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, cuisine, openingHour, owner, createdAt, lastUpdatedAt, active);
    }

    @Override
    public String toString() {
        return "Restaurant{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", address=" + address + ", cuisine='" + cuisine + '\'' + ", openingHour='" + openingHour + '\'' + ", owner=" + owner + ", createdAt=" + createdAt + ", lastUpdatedAt=" + lastUpdatedAt + ", active=" + active + '}';
    }

}
