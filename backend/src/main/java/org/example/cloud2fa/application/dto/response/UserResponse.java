package org.example.cloud2fa.application.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
            String id,
            String username,
            String email,
            LocalDateTime createdAt) {
}
