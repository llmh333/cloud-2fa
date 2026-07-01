package org.example.cloud2fa.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Login request data")
public class LoginRequestDto {

   @Schema(description = "Username or email for authentication", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED)
   private String username;

   @Schema(description = "User password", example = "Password@123", requiredMode = Schema.RequiredMode.REQUIRED)
   private String password;
}
