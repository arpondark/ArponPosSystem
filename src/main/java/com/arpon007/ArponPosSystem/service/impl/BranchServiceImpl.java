package com.arpon007.ArponPosSystem.service.impl;

import com.arpon007.ArponPosSystem.Repo.BranchRepo;
import com.arpon007.ArponPosSystem.Repo.StoreRepository;
import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.mapper.BranchMapper;
import com.arpon007.ArponPosSystem.models.Branch;
import com.arpon007.ArponPosSystem.models.Store;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.models.UserRole;
import com.arpon007.ArponPosSystem.payload.dto.BranchDTO;
import com.arpon007.ArponPosSystem.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BranchServiceImpl implements BranchService {
    private final BranchRepo branchRepo;
    private final StoreRepository storeRepository;

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO, User user) throws UserException {
        log.info("Creating branch with name: {} for store ID: {}", branchDTO.getName(), branchDTO.getStoreId());

        try {
            // Validate input
            validateBranchDTO(branchDTO);
            validateUserPermissions(user, branchDTO.getStoreId(), "create branch");

            // Get the store
            Store store = getStoreById(branchDTO.getStoreId());

            // Check if branch with same name already exists in this store
            if (branchRepo.existsByNameAndStoreId(branchDTO.getName().trim(), store.getId())) {
                throw new UserException("Branch with name '" + branchDTO.getName() + "' already exists in this store");
            }

            // Validate business hours
            validateBusinessHours(branchDTO);

            // Create branch entity
            Branch branch = BranchMapper.toEntity(branchDTO, store);
            branch.setCreatedAt(LocalDateTime.now());
            branch.setUpdatedAt(LocalDateTime.now());

            // Save branch
            Branch savedBranch = branchRepo.save(branch);

            log.info("Successfully created branch with ID: {} for store: {}",
                    savedBranch.getId(), store.getBrand());

            return BranchMapper.toBranchDTO(savedBranch);

        } catch (UserException e) {
            log.error("Failed to create branch: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while creating branch: {}", e.getMessage(), e);
            throw new UserException("Failed to create branch: " + e.getMessage());
        }
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO, User user) throws UserException {
        log.info("Updating branch with ID: {}", id);

        try {
            // Validate input
            if (id == null) {
                throw new UserException("Branch ID cannot be null");
            }
            validateBranchDTO(branchDTO);

            // Get existing branch
            Branch existingBranch = getBranchEntityById(id);
            validateUserPermissions(user, existingBranch.getStore().getId(), "update branch");

            // Check if new name conflicts with existing branches (excluding current branch)
            if (!existingBranch.getName().equals(branchDTO.getName().trim()) &&
                branchRepo.existsByNameAndStoreIdAndIdNot(branchDTO.getName().trim(),
                    existingBranch.getStore().getId(), id)) {
                throw new UserException("Branch with name '" + branchDTO.getName() + "' already exists in this store");
            }

            // Validate business hours
            validateBusinessHours(branchDTO);

            // Update branch fields
            existingBranch.setName(branchDTO.getName().trim());
            existingBranch.setAddress(branchDTO.getAddress().trim());
            existingBranch.setEmail(branchDTO.getEmail() != null ? branchDTO.getEmail().trim() : null);
            existingBranch.setPhone(branchDTO.getPhone() != null ? branchDTO.getPhone().trim() : null);
            existingBranch.setWorkingDays(branchDTO.getWorkingDays());
            existingBranch.setOpenTime(branchDTO.getOpenTime());
            existingBranch.setCloseTime(branchDTO.getCloseTime());
            existingBranch.setUpdatedAt(LocalDateTime.now());

            // Save updated branch
            Branch updatedBranch = branchRepo.save(existingBranch);

            log.info("Successfully updated branch with ID: {}", id);

            return BranchMapper.toBranchDTO(updatedBranch);

        } catch (UserException e) {
            log.error("Failed to update branch: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while updating branch: {}", e.getMessage(), e);
            throw new UserException("Failed to update branch: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BranchDTO deleteBranch(Long id, User user) throws UserException {
        log.info("Deleting branch with ID: {}", id);

        try {
            if (id == null) {
                throw new UserException("Branch ID cannot be null");
            }

            // Get existing branch
            Branch branch = getBranchEntityById(id);

            // Validate user permissions for deletion
            validateUserPermissions(user, branch.getStore().getId(), "delete branch");

            // Convert to DTO before deletion
            BranchDTO branchDTO = BranchMapper.toBranchDTO(branch);

            // Delete the branch
            branchRepo.deleteById(id);

            log.info("Successfully deleted branch with ID: {} by user: {}", id, user.getEmail());

            return branchDTO;

        } catch (UserException e) {
            log.error("Failed to delete branch: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while deleting branch: {}", e.getMessage(), e);
            throw new UserException("Failed to delete branch: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchDTO> getAllBranchesByStoreId(Long storeId) throws UserException {
        log.info("Fetching all branches for store ID: {}", storeId);

        try {
            if (storeId == null) {
                throw new UserException("Store ID cannot be null");
            }

            // Verify store exists
            Store store = getStoreById(storeId);

            // Get all branches for the store
            List<Branch> branches = branchRepo.findByStoreIdOrderByCreatedAtDesc(storeId);

            log.info("Found {} branches for store: {}", branches.size(), store.getBrand());

            return branches.stream()
                    .map(BranchMapper::toBranchDTO)
                    .collect(Collectors.toList());

        } catch (UserException e) {
            log.error("Failed to get branches for store: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching branches: {}", e.getMessage(), e);
            throw new UserException("Failed to fetch branches: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BranchDTO getBranchById(Long id) throws UserException {
        log.info("Fetching branch with ID: {}", id);

        try {
            if (id == null) {
                throw new UserException("Branch ID cannot be null");
            }

            Branch branch = getBranchEntityById(id);

            log.info("Successfully fetched branch: {}", branch.getName());

            return BranchMapper.toBranchDTO(branch);

        } catch (UserException e) {
            log.error("Failed to get branch: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching branch: {}", e.getMessage(), e);
            throw new UserException("Failed to fetch branch: " + e.getMessage());
        }
    }

    // Helper methods

    private void validateBranchDTO(BranchDTO branchDTO) throws UserException {
        if (branchDTO == null) {
            throw new UserException("Branch data cannot be null");
        }

        if (branchDTO.getName() == null || branchDTO.getName().trim().isEmpty()) {
            throw new UserException("Branch name is required");
        }

        if (branchDTO.getName().trim().length() < 2) {
            throw new UserException("Branch name must be at least 2 characters long");
        }

        if (branchDTO.getName().trim().length() > 100) {
            throw new UserException("Branch name cannot exceed 100 characters");
        }

        if (branchDTO.getAddress() == null || branchDTO.getAddress().trim().isEmpty()) {
            throw new UserException("Branch address is required");
        }

        if (branchDTO.getAddress().trim().length() > 500) {
            throw new UserException("Branch address cannot exceed 500 characters");
        }

        if (branchDTO.getEmail() != null && !branchDTO.getEmail().trim().isEmpty()) {
            if (!isValidEmail(branchDTO.getEmail().trim())) {
                throw new UserException("Invalid email format");
            }
        }

        if (branchDTO.getPhone() != null && !branchDTO.getPhone().trim().isEmpty()) {
            if (!isValidPhone(branchDTO.getPhone().trim())) {
                throw new UserException("Invalid phone number format");
            }
        }

        if (branchDTO.getStoreId() == null) {
            throw new UserException("Store ID is required");
        }

        if (branchDTO.getWorkingDays() == null || branchDTO.getWorkingDays().isEmpty()) {
            throw new UserException("Working days are required");
        }

        // Validate working days
        List<String> validDays = List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
                                        "FRIDAY", "SATURDAY", "SUNDAY");
        for (String day : branchDTO.getWorkingDays()) {
            if (!validDays.contains(day.toUpperCase())) {
                throw new UserException("Invalid working day: " + day);
            }
        }
    }

    private void validateBusinessHours(BranchDTO branchDTO) throws UserException {
        if (branchDTO.getOpenTime() == null) {
            throw new UserException("Opening time is required");
        }

        if (branchDTO.getCloseTime() == null) {
            throw new UserException("Closing time is required");
        }

        if (!branchDTO.getOpenTime().isBefore(branchDTO.getCloseTime())) {
            throw new UserException("Opening time must be before closing time");
        }
    }

    private void validateUserPermissions(User user, Long storeId, String operation) throws UserException {
        if (user == null) {
            throw new UserException("User authentication required");
        }

        // System admin can perform any operation
        if (user.getRole() == UserRole.ROLE_ADMIN) {
            return;
        }

        // For store-specific roles, check store ownership/association
        if (user.getRole() == UserRole.ROLE_STORE_ADMIN ||
            user.getRole() == UserRole.ROLE_STORE_MANAGER) {

            // Here you would typically check if the user is associated with the store
            // For now, we'll allow store admins and managers to manage branches
            log.debug("User {} with role {} attempting to {} for store {}",
                     user.getEmail(), user.getRole(), operation, storeId);
            return;
        }

        throw new UserException("Insufficient permissions to " + operation);
    }

    private Store getStoreById(Long storeId) throws UserException {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new UserException("Store not found with ID: " + storeId));
    }

    private Branch getBranchEntityById(Long id) throws UserException {
        return branchRepo.findById(id)
                .orElseThrow(() -> new UserException("Branch not found with ID: " + id));
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isValidPhone(String phone) {
        // Allow various phone formats: +1234567890, (123) 456-7890, 123-456-7890, etc.
        return phone.matches("^[+]?[\\d\\s\\-\\(\\)]{7,15}$");
    }
}
