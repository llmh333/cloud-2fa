package org.example.cloud2fa.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

import org.example.cloud2fa.domain.model.User;

public final class UserEvent {

   private UserEvent() {
   } // Prevent instantiation

   // Event: User was created
   public record Created(
         String eventId,
         String userId,
         String username,
         String email,
         String role,
         LocalDateTime occurredAt) implements DomainEvent<User> {

      // Convenience constructor
      public Created(String userId, String username, String email, String role) {
         this(UUID.randomUUID().toString(), userId, username, email, role, LocalDateTime.now());
      }

      @Override
      public String getEventId() {
         return eventId;
      }

      @Override
      public LocalDateTime getOccurredAt() {
         return occurredAt;
      }

      @Override
      public String getAggregateId() {
         return userId;
      }

      @Override
      public String getAggregateType() {
         return User.class.getSimpleName();
      }
   }

   // Event: User was deleted
   public record Deleted(
         String eventId,
         String userId,
         String username,
         LocalDateTime occurredAt) implements DomainEvent<User> {

      public Deleted(String userId, String username) {
         this(UUID.randomUUID().toString(), userId, username, LocalDateTime.now());
      }

      @Override
      public String getEventId() {
         return eventId;
      }

      @Override
      public LocalDateTime getOccurredAt() {
         return occurredAt;
      }

      @Override
      public String getAggregateId() {
         return userId;
      }

      @Override
      public String getAggregateType() {
         return User.class.getSimpleName();
      }
   }
}
