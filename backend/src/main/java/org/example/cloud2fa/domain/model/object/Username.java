package org.example.cloud2fa.domain.model.object;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object representing a username.
 * Immutable and self-validating.
 */
public record Username(String value) {

   private static final int MIN_LENGTH = 3;
   private static final int MAX_LENGTH = 50;
   private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

   public Username {
      Objects.requireNonNull(value, "Username cannot be null");
      if (value.isBlank()) {
         throw new IllegalArgumentException("Username cannot be blank");
      }
      if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
         throw new IllegalArgumentException(
               String.format("Username must be between %d and %d characters", MIN_LENGTH, MAX_LENGTH));
      }
      if (!USERNAME_PATTERN.matcher(value).matches()) {
         throw new IllegalArgumentException("Username can only contain letters, numbers, and underscores");
      }
   }

   @Override
   public String toString() {
      return value;
   }
}
