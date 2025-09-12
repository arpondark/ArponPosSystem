package com.arpon007.ArponPosSystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.CategoryDTO;
import com.arpon007.ArponPosSystem.service.CategoryService;
import com.arpon007.ArponPosSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO,
                                                     @RequestHeader("Authorization") String jwt) throws UserException {
        try {
            User user = userService.getUserFromJwtToken(jwt);
            CategoryDTO createdCategory = categoryService.createCategory(categoryDTO, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                     @RequestBody CategoryDTO categoryDTO,
                                                     @RequestHeader("Authorization") String jwt) throws UserException {
        try {
            User user = userService.getUserFromJwtToken(jwt);
            CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO, user);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id,
                                              @RequestHeader("Authorization") String jwt) throws UserException {
        try {
            User user = userService.getUserFromJwtToken(jwt);
            categoryService.deleteCategory(id, user);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByStore(@PathVariable Long storeId) {
        List<CategoryDTO> categories = categoryService.getCategoriesByStore(storeId);
        return ResponseEntity.ok(categories);
    }
}
