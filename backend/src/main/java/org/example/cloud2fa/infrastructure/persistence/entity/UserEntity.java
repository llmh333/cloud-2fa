package org.example.cloud2fa.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

   @Id
   private String id;

   @Column(unique = true, nullable = false, length = 50)
   private String username;

   @Column(nullable = false)
   private String password;

   @Column(name = "master_password", nullable = false)
   private String masterPassword;

   @Column(unique = true, nullable = false)
   private String email;

   @Column(nullable = false)
   private String role;

   @Column(name = "created_at", nullable = false)
   private LocalDateTime createdAt;

   @Column(name = "updated_at", nullable = false)
   private LocalDateTime updatedAt;
}
