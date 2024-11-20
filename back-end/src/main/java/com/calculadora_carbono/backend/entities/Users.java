package com.calculadora_carbono.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<EmissionActivity> emissionActivities;

    private LocalDateTime creationDate;
    private LocalDateTime changeDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.creationDate = now;
        this.changeDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.changeDate = LocalDateTime.now();
    }
}