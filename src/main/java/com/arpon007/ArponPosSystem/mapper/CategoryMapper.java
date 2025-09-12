package com.arpon007.ArponPosSystem.mapper;

import com.arpon007.ArponPosSystem.models.Category;
import com.arpon007.ArponPosSystem.payload.dto.CategoryDTO;

public class CategoryMapper {
    public static CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore() != null ? category.getStore().getId() : null)
                .build();
    }
}
