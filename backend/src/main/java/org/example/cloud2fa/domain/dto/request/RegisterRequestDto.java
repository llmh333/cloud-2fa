package org.example.cloud2fa.domain.dto.request;

import org.example.cloud2fa.constant.MessageKey;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User registration request data")
public class RegisterRequestDto {

   @Schema(description = "Username (6-20 alphanumeric characters)", example = "john_doe", minLength = 6, maxLength = 20, requiredMode = Schema.RequiredMode.REQUIRED)
   @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = MessageKey.ErrorMessage.Valid.USERNAME)
   private String username;

   @Schema(description = "Password (min 8 chars, must contain: uppercase, lowercase, number, special char)", example = "Password@123", minLength = 8, requiredMode = Schema.RequiredMode.REQUIRED)
   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = MessageKey.ErrorMessage.Valid.PASSWORD)
   private String password;

   @Schema(description = "Master password for encrypting TOTP secrets (same format as password)", example = "Master@456", minLength = 8, requiredMode = Schema.RequiredMode.REQUIRED)
   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = MessageKey.ErrorMessage.Valid.MASTER_PASSWORD)
   private String masterPassword;

   @Schema(description = "User email address", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
   @Email(message = MessageKey.ErrorMessage.Valid.EMAIL)
   private String email;

   @Schema(description = "Phone number (10 digits)", example = "0123456789", pattern = "^[0-9]{10}$")
   @Pattern(regexp = "^[0-9]{10}$", message = MessageKey.ErrorMessage.Valid.PHONE)
   private String phone;
}
