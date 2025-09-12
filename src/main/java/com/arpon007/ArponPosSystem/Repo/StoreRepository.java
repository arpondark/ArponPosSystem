package com.arpon007.ArponPosSystem.Repo;

import com.arpon007.ArponPosSystem.models.Store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByStoreAdminId(Long id);
}
