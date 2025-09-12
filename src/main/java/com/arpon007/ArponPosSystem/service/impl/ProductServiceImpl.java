package com.arpon007.ArponPosSystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arpon007.ArponPosSystem.Repo.CategoryRepo;
import com.arpon007.ArponPosSystem.Repo.ProductRepo;
import com.arpon007.ArponPosSystem.Repo.StoreRepository;
import com.arpon007.ArponPosSystem.mapper.ProductMapper;
import com.arpon007.ArponPosSystem.models.Category;
import com.arpon007.ArponPosSystem.models.Product;
import com.arpon007.ArponPosSystem.models.Store;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.ProductDTO;
import com.arpon007.ArponPosSystem.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final StoreRepository storeRepository;
    private final CategoryRepo categoryRepo;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, User user) {
        Store store = findStoreByIdOrThrow(productDTO.getStoreId());
        Category category = null;
        
        if (productDTO.getCategoryId() != null) {
            category = findCategoryByIdOrThrow(productDTO.getCategoryId());
        }

        Product product = Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .mrp(productDTO.getMrp())
                .sellingPrice(productDTO.getSellingPrice())
                .brand(productDTO.getBrand())
                .color(productDTO.getColor())
                .imageUrl(productDTO.getImageUrl())
                .description(productDTO.getDescription())
                .store(store)
                .category(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepo.save(product);
        return productMapper.mapToDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, User user) {
        Product existingProduct = findProductByIdOrThrow(id);
        Store store = findStoreByIdOrThrow(productDTO.getStoreId());
        
        Category category = null;
        if (productDTO.getCategoryId() != null) {
            category = findCategoryByIdOrThrow(productDTO.getCategoryId());
        }

        existingProduct.setName(productDTO.getName());
        existingProduct.setSku(productDTO.getSku());
        existingProduct.setMrp(productDTO.getMrp());
        existingProduct.setSellingPrice(productDTO.getSellingPrice());
        existingProduct.setBrand(productDTO.getBrand());
        existingProduct.setColor(productDTO.getColor());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setStore(store);
        existingProduct.setCategory(category);
        existingProduct.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepo.save(existingProduct);
        return productMapper.mapToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) {
        Product product = findProductByIdOrThrow(id);
        productRepo.delete(product);
    }

    @Override
    public List<ProductDTO> getProductsByStoreId(Long storeId) {
        List<Product> products = productRepo.findByStoreId(storeId);
        return mapToProductDTOList(products);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);
        return mapToProductDTOList(products);
    }

    @Override
    public List<ProductDTO> getProductsByStoreAndCategory(Long storeId, Long categoryId) {
        List<Product> products = productRepo.findByStoreIdAndCategoryId(storeId, categoryId);
        return mapToProductDTOList(products);
    }

    @Override
    public List<ProductDTO> searchByKeyword(Long storeId, String keyword) {
        List<Product> products = productRepo.searchByKeyword(storeId, keyword);
        return mapToProductDTOList(products);
    }

    private Product findProductByIdOrThrow(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private Store findStoreByIdOrThrow(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }

    private Category findCategoryByIdOrThrow(Long categoryId) {
        return categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private List<ProductDTO> mapToProductDTOList(List<Product> products) {
        return products.stream()
                .map(productMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}
