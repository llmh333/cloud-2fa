package org.example.cloud2fa.domain.model;

import java.time.LocalDateTime;

import org.example.cloud2fa.domain.model.object.Email;
import org.example.cloud2fa.domain.model.object.Password;
import org.example.cloud2fa.domain.model.object.Role;
import org.example.cloud2fa.domain.model.object.UserId;
import org.example.cloud2fa.domain.model.object.Username;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
   private UserId id;
   private Username username;
   private Password password;
   private Password masterPassword;
   private Email email;
   private Role role;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;

   public static User create(String username, String encodedPassword, String encodedMasterPassword, String email,
         Role role) {
      return new User(
            UserId.generate(),
            new Username(username),
            new Password(encodedPassword),
            new Password(encodedMasterPassword),
            new Email(email),
            role,
            LocalDateTime.now(),
            LocalDateTime.now());
   }

   public String getIdValue() {
      return id != null ? id.value() : null;
   }

   public String getUsernameValue() {
      return username != null ? username.value() : null;
   }

   public String getPasswordValue() {
      return password != null ? password.value() : null;
   }

   public String getMasterPasswordValue() {
      return masterPassword != null ? masterPassword.value() : null;
   }

   public String getEmailValue() {
      return email != null ? email.value() : null;
   }

   public Role getRoleValue() {
      return role != null ? role : null;
   }
}
