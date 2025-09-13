package com.arpon007.ArponPosSystem.Repo;

import com.arpon007.ArponPosSystem.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepo extends JpaRepository<Branch, Long> {

    /**
     * Find all branches by store ID
     */
    List<Branch> findByStoreId(Long storeId);

    /**
     * Find all branches by store ID ordered by creation date descending
     */
    List<Branch> findByStoreIdOrderByCreatedAtDesc(Long storeId);

    /**
     * Check if a branch with the given name exists in the specified store
     */
    boolean existsByNameAndStoreId(String name, Long storeId);

    /**
     * Check if a branch with the given name exists in the specified store, excluding the branch with given ID
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Branch b WHERE b.name = :name AND b.store.id = :storeId AND b.id != :id")
    boolean existsByNameAndStoreIdAndIdNot(@Param("name") String name, @Param("storeId") Long storeId, @Param("id") Long id);

    /**
     * Find branches by store ID and name containing (case insensitive search)
     */
    @Query("SELECT b FROM Branch b WHERE b.store.id = :storeId AND LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Branch> findByStoreIdAndNameContainingIgnoreCase(@Param("storeId") Long storeId, @Param("name") String name);

    /**
     * Find branches by manager user ID
     */
    @Query("SELECT b FROM Branch b WHERE b.manager.id = :managerId")
    List<Branch> findByManagerId(@Param("managerId") Long managerId);

    /**
     * Count branches by store ID
     */
    long countByStoreId(Long storeId);

    /**
     * Find branches by working days containing specific day
     */
    @Query("SELECT b FROM Branch b WHERE :day MEMBER OF b.workingDays AND b.store.id = :storeId")
    List<Branch> findByStoreIdAndWorkingDaysContaining(@Param("storeId") Long storeId, @Param("day") String day);
}
