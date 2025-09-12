package com.arpon007.ArponPosSystem.payload.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;

    private String sku;
    private Double mrp;
    private Double sellingPrice;
    private String brand;
    private String color;
    private String imageUrl;
    private String description;
    // private Category category;
    private Long categoryId;
    private Long storeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
