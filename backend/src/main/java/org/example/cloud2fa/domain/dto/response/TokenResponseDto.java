package org.example.cloud2fa.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {
   private String type = "Bearer";
   private String accessToken;
   private String refreshToken;
   private UserSummaryResponseDto user;
}
