package org.example.cloud2fa.domain.model.object;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String value) {

   private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

   public Email {
      Objects.requireNonNull(value, "Email cannot be null");
      if (value.isBlank()) {
         throw new IllegalArgumentException("Email cannot be blank");
      }
      if (!EMAIL_PATTERN.matcher(value).matches()) {
         throw new IllegalArgumentException("Invalid email format: " + value);
      }
   }

   @Override
   public String toString() {
      return value;
   }
}
