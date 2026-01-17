package org.example.cloud2fa.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.example.cloud2fa.domain.model.User;
import org.example.cloud2fa.domain.repository.UserRepository;
import org.example.cloud2fa.infrastructure.persistence.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

   private final JpaUserRepository jpaRepository;
   private final UserPersistenceMapper mapper;

   @Override
   public User save(User user) {
      var entity = mapper.toEntity(user);
      var saved = jpaRepository.save(entity);
      return mapper.toDomain(saved);
   }

   @Override
   public Optional<User> findById(String id) {
      return jpaRepository.findById(id).map(mapper::toDomain);
   }

   @Override
   public Optional<User> findByUsername(String username) {
      return jpaRepository.findByUsername(username).map(mapper::toDomain);
   }

   @Override
   public Optional<User> findByEmail(String email) {
      return jpaRepository.findByEmail(email).map(mapper::toDomain);
   }

   @Override
   public boolean existsByUsername(String username) {
      return jpaRepository.existsByUsername(username);
   }

   @Override
   public boolean existsByEmail(String email) {
      return jpaRepository.existsByEmail(email);
   }

   @Override
   public List<User> findAll() {
      return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
   }

   @Override
   public void deleteById(String id) {
      jpaRepository.deleteById(id);
   }
}
