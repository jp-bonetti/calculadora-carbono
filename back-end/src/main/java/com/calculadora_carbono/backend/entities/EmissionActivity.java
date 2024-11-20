package com.calculadora_carbono.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@ToString(exclude = {"users", "category"})
public class EmissionActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Category category;

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
