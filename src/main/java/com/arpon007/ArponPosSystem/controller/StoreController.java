package com.arpon007.ArponPosSystem.controller;

import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.StoreDto;
import com.arpon007.ArponPosSystem.service.StoreService;
import com.arpon007.ArponPosSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDto> createStore(@RequestBody StoreDto storeDto,
                                              @RequestHeader("Authorization") String jwt) throws UserException {
        // Add some debugging
        System.out.println("Received StoreDto: " + storeDto);
        System.out.println("JWT: " + jwt);

        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDto, user));
    }



    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStore());
    }

    @GetMapping("/admin")
    public ResponseEntity<StoreDto> getStoreByAdmin(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.getAllStoreByAdmin(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> updateStore(@PathVariable Long id,
                                              @RequestBody StoreDto storeDto) {
        return ResponseEntity.ok(storeService.updateStore(id, storeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StoreDto> deleteStore(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.deleteStore(id));
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDto> getStoreByEmployee(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.getStoreByEmployee(user));
    }
}
