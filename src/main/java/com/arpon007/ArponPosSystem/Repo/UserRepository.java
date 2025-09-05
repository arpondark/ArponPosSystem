package com.arpon007.ArponPosSystem.Repo;

import com.arpon007.ArponPosSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String username);
}
