package org.example.cloud2fa.application.dto.response;

import java.time.LocalDateTime;

public record LoginResponse(
      String accessToken,
      String refreshToken,
      String tokenType,
      UserResponse user,
      LocalDateTime issuedAt) {

   public static LoginResponse of(String accessToken, String refreshToken, UserResponse user) {
      return new LoginResponse(
            accessToken,
            refreshToken,
            "Bearer",
            user,
            LocalDateTime.now());
   }
}
