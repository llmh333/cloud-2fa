package org.example.cloud2fa.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponeDto {
   private String id;
   private String username;
   private String email;
   private String phone;
   private String role;
   private String status;
   private String createdAt;
   private String updatedAt;
}
