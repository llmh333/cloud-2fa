package org.example.cloud2fa.infrastructure.event.handler;

import org.example.cloud2fa.domain.event.UserEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Event handler for User-related domain events.
 * Handles side effects like sending emails, logging, notifications.
 */
@Slf4j
@Component
public class UserEventHandler {

   /**
    * Handles UserEvent.Created - runs asynchronously.
    * Example: Send welcome email, create audit log, etc.
    */
   @Async
   @EventListener
   public void onUserCreated(UserEvent.Created event) {
      log.info("Handling UserCreated event: userId={}, username={}, email={}",
            event.userId(),
            event.username(),
            event.email());

      // TODO: Implement side effects
      // - Send welcome email
      // - Create audit log
      // - Update analytics
   }

   /**
    * Handles UserEvent.Deleted - runs asynchronously.
    */
   @Async
   @EventListener
   public void onUserDeleted(UserEvent.Deleted event) {
      log.info("Handling UserDeleted event: userId={}, username={}",
            event.userId(),
            event.username());

      // TODO: Implement side effects
      // - Send goodbye email
      // - Archive user data
      // - Update analytics
   }
}
