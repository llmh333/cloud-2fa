package org.example.cloud2fa.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Authentication token response")
public class TokenResponseDto {

   @Schema(description = "Token type", example = "Bearer", defaultValue = "Bearer")
   private String type = "Bearer";

   @Schema(description = "JWT access token for API authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
   private String accessToken;

   @Schema(description = "JWT refresh token for obtaining new access tokens", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
   private String refreshToken;

   @Schema(description = "Authenticated user summary information")
   private UserSummaryResponseDto user;
}
