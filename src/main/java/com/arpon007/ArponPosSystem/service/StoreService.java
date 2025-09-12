package com.arpon007.ArponPosSystem.service;

import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto createStore(StoreDto storeDto, User user);
    StoreDto getStoreById(Long id);
    List<StoreDto> getAllStore();
    StoreDto getAllStoreByAdmin(User user);
    StoreDto updateStore(Long id,StoreDto storeDto);
    StoreDto deleteStore(Long id);
    StoreDto getStoreByEmployee(User user);

}
