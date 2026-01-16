package org.example.cloud2fa.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SecretKey {
   private String id;
   private String key;
   private User user;
   private LocalDateTime createdAt;
   private boolean isDeleted;
   private LocalDateTime isDeletedAt;
}
