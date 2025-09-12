package com.arpon007.ArponPosSystem.Repo;

import com.arpon007.ArponPosSystem.models.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    List<Category> findByStoreId(Long id);
}
