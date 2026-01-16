package org.example.cloud2fa.domain.repository;

import java.util.Optional;

import org.example.cloud2fa.domain.model.User;

public interface UserRepository extends BaseRepository<User, String> {
   Optional<User> findByUsername(String username);

   Optional<User> findByEmail(String email);

   boolean existsByUsername(String username);

   boolean existsByEmail(String email);
}
