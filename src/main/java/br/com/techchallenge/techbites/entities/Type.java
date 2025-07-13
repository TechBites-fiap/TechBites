package br.com.techchallenge.techbites.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_items_type")
@SQLDelete(sql = "UPDATE tb_items_type SET active = false WHERE id_item_type = ?")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_type")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (o == null || getClass() != o.getClass()) return false;
        Type type1 = (Type) o;
        return id == type1.id && Objects.equals(type, type1.type) && Objects.equals(createdAt, type1.createdAt) && Objects.equals(lastUpdatedAt, type1.lastUpdatedAt) && Objects.equals(active, type1.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, createdAt, lastUpdatedAt, active);
    }

    @Override
    public String toString() {
        return "Type{" + "id=" + id + ", type='" + type + '\'' + ", createdAt=" + createdAt + ", lastUpdatedAt=" + lastUpdatedAt + ", active=" + active + '}';
    }
}
