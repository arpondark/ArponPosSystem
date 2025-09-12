package com.arpon007.ArponPosSystem.mapper;

import org.springframework.stereotype.Component;

import com.arpon007.ArponPosSystem.models.Category;
import com.arpon007.ArponPosSystem.models.Product;
import com.arpon007.ArponPosSystem.models.Store;
import com.arpon007.ArponPosSystem.payload.dto.ProductDTO;

@Component
public class ProductMapper {
    public ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .brand(product.getBrand())
                .color(product.getColor())
                .storeId(product.getStore() != null ? product.getStore().getId() : null)
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .build();
    }

    public Product toEntity(ProductDTO productDTO, Store store, Category category) {
        return Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .description(productDTO.getDescription())
                .mrp(productDTO.getMrp())
                .sellingPrice(productDTO.getSellingPrice())
                .brand(productDTO.getBrand())
                .color(productDTO.getColor())
                .imageUrl(productDTO.getImageUrl())
                .store(store)
                .category(category)
                .build();
    }
}
