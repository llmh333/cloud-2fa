package org.example.cloud2fa.infrastructure.event;

import org.example.cloud2fa.application.port.out.DomainEventPublisher;
import org.example.cloud2fa.domain.event.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring-based implementation of DomainEventPublisher.
 * Uses Spring's ApplicationEventPublisher internally.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpringDomainEventPublisher implements DomainEventPublisher {

   private final ApplicationEventPublisher springEventPublisher;

   @Override
   public void publish(DomainEvent<?> event) {
      log.info("Publishing domain event: {} [id={}]",
            event.getClass().getSimpleName(),
            event.getEventId());
      springEventPublisher.publishEvent(event);
   }
}
