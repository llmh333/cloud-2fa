package org.example.cloud2fa.domain.entity;

import org.example.cloud2fa.constant.enums.AccountStatusEnum;
import org.example.cloud2fa.constant.enums.RoleEnum;
import org.example.cloud2fa.domain.entity.common.DateAuditing;
import org.hibernate.annotations.UuidGenerator;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends DateAuditing {

   @Id
   @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
   private String id;

   @Column(nullable = false, unique = true)
   private String username;

   @Column(nullable = false)
   private String password;

   @Column
   private String masterPassword;

   @Column(nullable = false, unique = true)
   private String email;

   @Column(nullable = false, unique = true)
   private String phone;

   @Column(nullable = false)
   private RoleEnum role;

   @Column(nullable = false)
   private AccountStatusEnum status;
}
