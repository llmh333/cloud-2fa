package org.example.cloud2fa.application.dto.request;

public record CreateUserRequest(
      String username,
      String password,
      String masterPassword,
      String email) {
}
