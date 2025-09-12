package com.arpon007.ArponPosSystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.ProductDTO;
import com.arpon007.ArponPosSystem.service.ProductService;
import com.arpon007.ArponPosSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO,
                                                   @RequestHeader("Authorization") String jwt) throws UserException {
        try {
            User user = userService.getUserFromJwtToken(jwt);
            ProductDTO createdProduct = productService.createProduct(productDTO, user);
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            throw new UserException("Failed to create product: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                   @RequestBody ProductDTO productDTO,
                                                   @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(productService.updateProduct(id, productDTO, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id,
                                             @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        productService.deleteProduct(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDTO>> getProductsByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(productService.getProductsByStoreId(storeId));
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@PathVariable Long storeId,
                                                          @RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchByKeyword(storeId, keyword));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
    }

    @GetMapping("/store/{storeId}/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByStoreAndCategory(@PathVariable Long storeId, 
                                                                         @PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByStoreAndCategory(storeId, categoryId));
    }
}
