package com.arpon007.ArponPosSystem.service;

import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO, User user) throws Exception;

    List<CategoryDTO> getCategoriesByStore(Long storeId);

    CategoryDTO updateCategory(Long id, CategoryDTO dto, User user) throws Exception;

    void deleteCategory(Long id, User user) throws Exception;
}
