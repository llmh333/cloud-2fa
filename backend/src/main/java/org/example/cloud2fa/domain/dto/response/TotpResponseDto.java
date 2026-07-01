package org.example.cloud2fa.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "TOTP code response")
public class TotpResponseDto {

   @Schema(description = "TOTP account unique identifier", example = "01HXYZ123456789ABCDEF")
   private String id;

   @Schema(description = "Account name or label", example = "john.doe@example.com")
   private String accountName;

   @Schema(description = "Service provider or issuer name", example = "Google")
   private String issuer;

   @Schema(description = "Current TOTP code", example = "123456")
   private String code;

   @Schema(description = "TOTP code validity period in seconds", example = "30")
   private int period;

   @Schema(description = "Remaining seconds until code expires", example = "15")
   private long remainingSeconds;
}
