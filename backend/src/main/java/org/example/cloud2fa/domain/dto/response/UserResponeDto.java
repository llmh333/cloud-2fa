package org.example.cloud2fa.domain.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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

   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
   private LocalDateTime createdAt;

   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
   private LocalDateTime updatedAt;
}
