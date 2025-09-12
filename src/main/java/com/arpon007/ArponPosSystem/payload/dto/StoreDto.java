package com.arpon007.ArponPosSystem.payload.dto;

import com.arpon007.ArponPosSystem.models.StoreContract;
import com.arpon007.ArponPosSystem.models.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private Long id;

    private String brand;

    private UserDto storeAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private String storeType;
    private StoreStatus status;

    private StoreContract contract;
}
