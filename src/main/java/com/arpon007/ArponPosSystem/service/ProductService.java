package com.arpon007.ArponPosSystem.service;

import java.util.List;

import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.ProductDTO;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO, User user);
    ProductDTO updateProduct(Long id, ProductDTO productDTO, User user);
    void deleteProduct(Long id, User user);
    List<ProductDTO> getProductsByStoreId(Long storeId);
    List<ProductDTO> getProductsByCategoryId(Long categoryId);
    List<ProductDTO> getProductsByStoreAndCategory(Long storeId, Long categoryId);
    List<ProductDTO> searchByKeyword(Long storeId, String keyword);
}
