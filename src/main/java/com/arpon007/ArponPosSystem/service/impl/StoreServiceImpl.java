package com.arpon007.ArponPosSystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arpon007.ArponPosSystem.Repo.StoreRepository;
import com.arpon007.ArponPosSystem.mapper.StoreMapper;
import com.arpon007.ArponPosSystem.models.Store;
import com.arpon007.ArponPosSystem.models.StoreStatus;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.StoreDto;
import com.arpon007.ArponPosSystem.service.StoreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public StoreDto createStore(StoreDto storeDto, User user) {
        Store store = StoreMapper.toEntity(storeDto, user);
        // Set creation timestamp and initial status manually since onCreate() is protected
        store.setCreatedAt(LocalDateTime.now());
        store.setStatus(StoreStatus.PENDING);
        return StoreMapper.toDto(storeRepository.save(store));
    }

    @Override
    public StoreDto getStoreById(Long id) {
        Store store = findStoreByIdOrThrow(id);
        return StoreMapper.toDto(store);
    }

    @Override
    public List<StoreDto> getAllStore() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(StoreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StoreDto getAllStoreByAdmin(User user) {
        Store store = findStoreByAdminOrThrow(user);
        return StoreMapper.toDto(store);
    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) {
        Store existingStore = findStoreByIdOrThrow(id);

        // Update store fields
        existingStore.setBrand(storeDto.getBrand());
        existingStore.setDescription(storeDto.getDescription());
        existingStore.setStoreType(storeDto.getStoreType());
        existingStore.setContract(storeDto.getContract());
        existingStore.setStatus(storeDto.getStatus());
        // Set update timestamp manually since onUpdate() is protected
        existingStore.setUpdatedAt(LocalDateTime.now());

        return StoreMapper.toDto(storeRepository.save(existingStore));
    }

    @Override
    public StoreDto deleteStore(Long id) {
        Store store = findStoreByIdOrThrow(id);
        StoreDto storeDto = StoreMapper.toDto(store);
        storeRepository.delete(store);
        return storeDto;
    }

    @Override
    public StoreDto getStoreByEmployee(User user) {
        // Assuming user has a reference to the store they work for
        // If not, you'll need to adjust this implementation based on your domain model
        Store store = findStoreByAdminOrThrow(user);
        return StoreMapper.toDto(store);
    }

    private Store findStoreByIdOrThrow(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + id));
    }

    private Store findStoreByAdminOrThrow(User user) {
        Store store = storeRepository.findByStoreAdminId(user.getId());
        if (store == null) {
            throw new RuntimeException("Store not found for user: " + user.getId());
        }
        return store;
    }
}
