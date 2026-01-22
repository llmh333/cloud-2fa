package org.example.cloud2fa.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotpSyncDataDto {
   private String id;
   private String accountName;
   private String issuer;
   private String secretBlob;
   private int digits;
   private int period;
   private String algorithm;
   private String encryptionSalt;
}
