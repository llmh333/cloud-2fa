package org.example.cloud2fa.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "User summary information")
public class UserSummaryResponseDto {

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
}
