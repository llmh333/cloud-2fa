package org.example.cloud2fa.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSummaryResponseDto {
   private String id;
   private String username;
   private String email;
   private String phone;
   private String role;
   private String status;
}
