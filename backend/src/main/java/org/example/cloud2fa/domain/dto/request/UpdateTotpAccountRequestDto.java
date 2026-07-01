package org.example.cloud2fa.domain.dto.request;

import org.example.cloud2fa.constant.MessageKey;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request to update a TOTP account")
public class UpdateTotpAccountRequestDto {

   @Schema(description = "Master password for authentication", example = "Master@456", requiredMode = Schema.RequiredMode.REQUIRED)
   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String masterPassword;

   @Schema(description = "New account name or label", example = "john.doe@newmail.com")
   private String accountName;

   @Schema(description = "New service provider or issuer name", example = "GitHub")
   private String issuer;

   @Schema(description = "New HMAC algorithm (HmacSHA1, HmacSHA256, HmacSHA512)", example = "HmacSHA256")
   private String algorithm;

   @Schema(description = "New number of digits in TOTP code", example = "6", minimum = "6")
   @Min(value = 6, message = MessageKey.ErrorMessage.Valid.MIN)
   private Integer digits;

   @Schema(description = "New TOTP code validity period in seconds", example = "30", minimum = "30")
   @Min(value = 30, message = MessageKey.ErrorMessage.Valid.MIN)
   private Integer period;
}
