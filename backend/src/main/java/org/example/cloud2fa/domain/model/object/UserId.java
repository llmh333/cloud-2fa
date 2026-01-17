package org.example.cloud2fa.domain.model.object;

import java.util.Objects;

import com.github.f4b6a3.uuid.UuidCreator;

public record UserId(String value) {

   public UserId {
      Objects.requireNonNull(value, "UserId cannot be null");
      if (value.isBlank()) {
         throw new IllegalArgumentException("UserId cannot be blank");
      }
   }

   public static UserId generate() {
      return new UserId(UuidCreator.getTimeOrderedEpoch().toString());
   }

   @Override
   public String toString() {
      return value;
   }
}
