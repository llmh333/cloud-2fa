package org.example.cloud2fa.repository;

import java.util.Optional;

import org.example.cloud2fa.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

   Optional<User> findByUsername(String username);

   boolean existsByUsername(String username);

   boolean existsByEmail(String email);

   boolean existsByPhone(String phone);
}
