package org.example.cloud2fa.domain.dto.request;

import org.example.cloud2fa.constant.MessageKey;
import org.example.cloud2fa.constant.enums.DefaultAlgorithm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request to add a new TOTP secret key")
public class AddSecretKeyRequestDto {

   @Schema(description = "Master password for encrypting the secret key", example = "Master@456", requiredMode = Schema.RequiredMode.REQUIRED)
   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String masterPassword;

   @Schema(description = "Base32-encoded TOTP secret key", example = "JBSWY3DPEHPK3PXP", requiredMode = Schema.RequiredMode.REQUIRED)
   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String secretKey;

   @Schema(description = "Account name or label", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String name;

   @Schema(description = "Service provider or issuer name", example = "Google", requiredMode = Schema.RequiredMode.REQUIRED)
   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String issuer;

   @Schema(description = "HMAC algorithm (HmacSHA1, HmacSHA256, HmacSHA512)", example = "HmacSHA1", defaultValue = "HmacSHA1")
   private String algorithm = DefaultAlgorithm.HmacSHA1.name();

   @Schema(description = "Number of digits in TOTP code", example = "6", minimum = "6", defaultValue = "6")
   @Min(value = 6, message = MessageKey.ErrorMessage.Valid.MIN)
   private int digits;

   @Schema(description = "TOTP code validity period in seconds", example = "30", minimum = "30", defaultValue = "30")
   @Min(value = 30, message = MessageKey.ErrorMessage.Valid.MIN)
   private int period = 30;
}
