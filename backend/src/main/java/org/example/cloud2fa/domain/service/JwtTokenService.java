package org.example.cloud2fa.domain.service;

import org.example.cloud2fa.domain.model.User;

public interface JwtTokenService {

   String generateToken(User user, boolean isRefreshToken);

   String extractUserId(String token);

   String extractUsername(String token);

   boolean isTokenValid(String token);

   long getExpirationTime();
}
