package com.ayaco.spotifou.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Table(name = "users")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @Column(name = "picture_url")
    private String pictureUrl;


    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    private void setCreatedAndUpdated() {
        Instant date = Instant.now();
        this.setCreatedAt(date);
        this.setUpdatedAt(date);
    }

    @PreUpdate
    private void updateUser() {
        this.setUpdatedAt(Instant.now());
    }

}
