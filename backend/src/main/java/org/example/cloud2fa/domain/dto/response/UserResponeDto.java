package org.example.cloud2fa.domain.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User information response")
public class UserResponeDto {

   @Schema(description = "Unique user identifier", example = "01HXYZ123456789ABCDEF")
   private String id;

   @Schema(description = "Username", example = "john_doe")
   private String username;

   @Schema(description = "User email address", example = "john.doe@example.com")
   private String email;

   @Schema(description = "User phone number", example = "0123456789")
   private String phone;

   @Schema(description = "User role", example = "USER")
   private String role;

   @Schema(description = "User account status", example = "ACTIVE")
   private String status;

   @Schema(description = "Account creation timestamp", example = "2024-01-15T10:30:00.0000000")
   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
   private LocalDateTime createdAt;

   @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00.0000000")
   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
   private LocalDateTime updatedAt;
}
