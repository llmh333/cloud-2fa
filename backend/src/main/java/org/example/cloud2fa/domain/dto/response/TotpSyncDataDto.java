package org.example.cloud2fa.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "TOTP sync data for offline usage")
public class TotpSyncDataDto {

   @Schema(description = "TOTP account unique identifier", example = "01HXYZ123456789ABCDEF")
   private String id;

   @Schema(description = "Account name or label", example = "john.doe@example.com")
   private String accountName;

   @Schema(description = "Service provider or issuer name", example = "Google")
   private String issuer;

   @Schema(description = "Encrypted secret key blob (Base64 encoded)", example = "SGVsbG9Xb3JsZA==")
   private String secretBlob;

   @Schema(description = "Number of digits in TOTP code", example = "6")
   private int digits;

   @Schema(description = "TOTP code validity period in seconds", example = "30")
   private int period;

   @Schema(description = "HMAC algorithm used", example = "HmacSHA1")
   private String algorithm;

   @Schema(description = "Encryption salt for key derivation (Base64 encoded)", example = "U2FsdGVkX18=")
   private String encryptionSalt;
}
