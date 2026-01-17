package org.example.cloud2fa.infrastructure.persistence.repository;

import java.util.Optional;

import org.example.cloud2fa.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {

   Optional<UserEntity> findByUsername(String username);

   Optional<UserEntity> findByEmail(String email);

   boolean existsByUsername(String username);

   boolean existsByEmail(String email);
}
