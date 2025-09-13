package com.arpon007.ArponPosSystem.controller;

import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.BranchDTO;
import com.arpon007.ArponPosSystem.service.BranchService;
import com.arpon007.ArponPosSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@Slf4j
public class BranchController {

    private final BranchService branchService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(
            @RequestBody BranchDTO branchDTO,
            @RequestHeader("Authorization") String jwt) throws UserException {

        log.info("Creating new branch: {}", branchDTO.getName());

        try {
            User user = userService.getUserFromJwtToken(jwt);
            BranchDTO createdBranch = branchService.createBranch(branchDTO, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);

        } catch (UserException e) {
            log.error("Failed to create branch: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error creating branch: {}", e.getMessage(), e);
            throw new UserException("Failed to create branch: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchDTO branchDTO,
            @RequestHeader("Authorization") String jwt) throws UserException {

        log.info("Updating branch with ID: {}", id);

        try {
            User user = userService.getUserFromJwtToken(jwt);
            BranchDTO updatedBranch = branchService.updateBranch(id, branchDTO, user);
            return ResponseEntity.ok(updatedBranch);

        } catch (UserException e) {
            log.error("Failed to update branch: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error updating branch: {}", e.getMessage(), e);
            throw new UserException("Failed to update branch: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BranchDTO> deleteBranch(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws UserException {

        log.info("Deleting branch with ID: {}", id);

        try {
            User user = userService.getUserFromJwtToken(jwt);
            BranchDTO deletedBranch = branchService.deleteBranch(id, user);
            return ResponseEntity.ok(deletedBranch);

        } catch (UserException e) {
            log.error("Failed to delete branch: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error deleting branch: {}", e.getMessage(), e);
            throw new UserException("Failed to delete branch: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) throws UserException {

        log.info("Fetching branch with ID: {}", id);

        try {
            BranchDTO branch = branchService.getBranchById(id);
            return ResponseEntity.ok(branch);

        } catch (UserException e) {
            log.error("Failed to fetch branch: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error fetching branch: {}", e.getMessage(), e);
            throw new UserException("Failed to fetch branch: " + e.getMessage());
        }
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDTO>> getBranchesByStore(@PathVariable Long storeId) throws UserException {

        log.info("Fetching all branches for store ID: {}", storeId);

        try {
            List<BranchDTO> branches = branchService.getAllBranchesByStoreId(storeId);
            return ResponseEntity.ok(branches);

        } catch (UserException e) {
            log.error("Failed to fetch branches for store: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error fetching branches: {}", e.getMessage(), e);
            throw new UserException("Failed to fetch branches for store: " + e.getMessage());
        }
    }

    @GetMapping("/store/{storeId}/count")
    public ResponseEntity<Long> getBranchCountByStore(@PathVariable Long storeId) throws UserException {

        log.info("Counting branches for store ID: {}", storeId);

        try {
            List<BranchDTO> branches = branchService.getAllBranchesByStoreId(storeId);
            return ResponseEntity.ok((long) branches.size());

        } catch (UserException e) {
            log.error("Failed to count branches for store: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error counting branches: {}", e.getMessage(), e);
            throw new UserException("Failed to count branches for store: " + e.getMessage());
        }
    }
}
