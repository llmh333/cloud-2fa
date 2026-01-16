package org.example.cloud2fa.domain.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {

   List<T> findAll();

   T save(T entity);

   Optional<T> findById(ID id);

   void deleteById(ID id);
}
