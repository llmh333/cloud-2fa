package org.example.cloud2fa.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
   private String username;
   private String password;
   private String masterPassword;
   private String email;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
}
