package org.example.cloud2fa.domain.model.object;

import java.util.Objects;

public record Password(String value) {

   public Password {
      Objects.requireNonNull(value, "Password cannot be null");
      if (value.isBlank()) {
         throw new IllegalArgumentException("Password cannot be blank");
      }
   }

   public static void validatePlainText(String plainPassword) {
      Objects.requireNonNull(plainPassword, "Password cannot be null");

      if (plainPassword.length() < 8) {
         throw new IllegalArgumentException("Password must be at least 8 characters");
      }
      if (!plainPassword.matches(".*[A-Z].*")) {
         throw new IllegalArgumentException("Password must contain at least one uppercase letter");
      }
      if (!plainPassword.matches(".*[a-z].*")) {
         throw new IllegalArgumentException("Password must contain at least one lowercase letter");
      }
      if (!plainPassword.matches(".*\\d.*")) {
         throw new IllegalArgumentException("Password must contain at least one digit");
      }
   }

   @Override
   public String toString() {
      return "[PROTECTED]";
   }
}
