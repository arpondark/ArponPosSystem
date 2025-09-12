package com.arpon007.ArponPosSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String brand;

    @OneToOne(cascade = CascadeType.ALL)
    private User storeAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private String storeType;
    private StoreStatus status;

    @Embedded
    private StoreContract contract = new StoreContract();

    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = StoreStatus.PENDING;
    }
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
