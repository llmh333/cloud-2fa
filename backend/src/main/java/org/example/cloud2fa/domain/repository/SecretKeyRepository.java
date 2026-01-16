package org.example.cloud2fa.domain.repository;

import java.util.List;

import org.example.cloud2fa.domain.model.SecretKey;

public interface SecretKeyRepository extends BaseRepository<SecretKey, String> {
   List<SecretKey> findByUserId(String userId);

}
