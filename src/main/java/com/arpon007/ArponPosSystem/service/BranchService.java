package com.arpon007.ArponPosSystem.service;

import com.arpon007.ArponPosSystem.exception.UserException;
import com.arpon007.ArponPosSystem.models.User;
import com.arpon007.ArponPosSystem.payload.dto.BranchDTO;

import java.util.List;

public interface BranchService {
    BranchDTO createBranch(BranchDTO branchDTO, User user) throws UserException;
    BranchDTO updateBranch(Long id, BranchDTO branchDTO, User user) throws UserException;
    BranchDTO deleteBranch(Long id, User user) throws UserException;
    List<BranchDTO> getAllBranchesByStoreId(Long storeId) throws UserException;
    BranchDTO getBranchById(Long id) throws UserException;
}
