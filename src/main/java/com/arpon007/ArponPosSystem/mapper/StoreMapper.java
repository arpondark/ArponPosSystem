package com.arpon007.ArponPosSystem.mapper;

import com.arpon007.ArponPosSystem.models.Store;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.StoreDto;

public class StoreMapper {
    public static StoreDto toDto(Store store) {
        StoreDto storeDto = new StoreDto();
        storeDto.setId(store.getId());
        storeDto.setBrand(store.getBrand());
        storeDto.setDescription(store.getDescription());
        storeDto.setStoreAdmin(UserMapper.toDto(store.getStoreAdmin()));
        storeDto.setStoreType(store.getStoreType());
        storeDto.setContract(store.getContract());
        storeDto.setCreatedAt(store.getCreatedAt());
        storeDto.setUpdatedAt(store.getUpdatedAt());
        storeDto.setStatus(store.getStatus());

        return storeDto;
    }

    public static Store toEntity(StoreDto storeDto, User storeAdmin) {
        Store store = new Store();
        store.setId(storeDto.getId());
        store.setBrand(storeDto.getBrand());
        store.setDescription(storeDto.getDescription());
        store.setStoreAdmin(storeAdmin);
        store.setStoreType(storeDto.getStoreType());
        store.setContract(storeDto.getContract());
        store.setCreatedAt(storeDto.getCreatedAt());
        store.setUpdatedAt(storeDto.getUpdatedAt());
        store.setStatus(storeDto.getStatus());

        return store;
    }
}
