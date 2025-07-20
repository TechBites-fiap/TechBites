package br.com.techchallenge.techbites.infrastructure.persistence;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_items_type")
@SQLDelete(sql = "UPDATE tb_items_type SET active = false WHERE id_item_type = ?")
public class TypeJpaEntity {

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
        TypeJpaEntity that = (TypeJpaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(createdAt, that.createdAt) && Objects.equals(lastUpdatedAt, that.lastUpdatedAt) && Objects.equals(active, that.active);
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
