package com.arpon007.ArponPosSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arpon007.ArponPosSystem.Repo.CategoryRepo;
import com.arpon007.ArponPosSystem.Repo.StoreRepository;
import com.arpon007.ArponPosSystem.mapper.CategoryMapper;
import com.arpon007.ArponPosSystem.models.Category;
import com.arpon007.ArponPosSystem.models.Store;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.models.UserRole;
import com.arpon007.ArponPosSystem.payload.dto.CategoryDTO;
import com.arpon007.ArponPosSystem.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final StoreRepository storeRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO, User user) throws Exception {
        Store store = findStoreByIdOrThrow(categoryDTO.getStoreId());
        checkAuthority(user, store);

        Category category = Category.builder()
                .name(categoryDTO.getName())
                .store(store)
                .build();

        Category savedCategory = categoryRepo.save(category);
        return CategoryMapper.toCategoryDTO(savedCategory);
    }

    @Override
    public List<CategoryDTO> getCategoriesByStore(Long storeId) {
        List<Category> categories = categoryRepo.findByStoreId(storeId);
        return categories.stream()
                .map(CategoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto, User user) throws Exception {
        Category existingCategory = findCategoryByIdOrThrow(id);
        checkAuthority(user, existingCategory.getStore());

        // Update fields
        existingCategory.setName(dto.getName());

        // Update store if provided
        if (dto.getStoreId() != null && !dto.getStoreId().equals(existingCategory.getStore().getId())) {
            Store newStore = findStoreByIdOrThrow(dto.getStoreId());
            checkAuthority(user, newStore); // Check auth for new store too
            existingCategory.setStore(newStore);
        }

        Category updatedCategory = categoryRepo.save(existingCategory);
        return CategoryMapper.toCategoryDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id, User user) throws Exception {
        Category category = findCategoryByIdOrThrow(id);
        checkAuthority(user, category.getStore());
        categoryRepo.deleteById(id);
    }

    private Category findCategoryByIdOrThrow(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    private Store findStoreByIdOrThrow(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + storeId));
    }

    private void checkAuthority(User user, Store store) throws Exception {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.getId().equals(store.getStoreAdmin().getId());

        if (!(isAdmin && isSameStore) && !isManager) {
            throw new Exception("You don't have permission to manage this category");
        }
    }
}
