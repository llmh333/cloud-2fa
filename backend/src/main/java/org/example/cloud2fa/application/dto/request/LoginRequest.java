package org.example.cloud2fa.application.dto.request;

public record LoginRequest(
      String username,
      String password) {
}
