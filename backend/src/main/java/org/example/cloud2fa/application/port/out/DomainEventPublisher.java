package org.example.cloud2fa.application.port.out;

import org.example.cloud2fa.domain.event.DomainEvent;

/**
 * Output port for publishing domain events.
 * Implementation will be in infrastructure layer (Spring Events, Kafka, etc.)
 */
public interface DomainEventPublisher {

   /**
    * Publishes a domain event to all registered handlers.
    *
    * @param event the domain event to publish
    */
   void publish(DomainEvent<?> event);
}
