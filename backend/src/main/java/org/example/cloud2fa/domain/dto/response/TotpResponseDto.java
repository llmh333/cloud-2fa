package org.example.cloud2fa.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotpResponseDto {
   private String id;
   private String accountName;
   private String issuer;
   private String code;
   private int period;
   private long remainingSeconds;
}
