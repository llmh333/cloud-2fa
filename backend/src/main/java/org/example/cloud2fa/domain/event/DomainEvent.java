package org.example.cloud2fa.domain.event;

import java.time.LocalDateTime;

public interface DomainEvent<T> {
   String getEventId();

   LocalDateTime getOccurredAt();

   String getAggregateId();

   String getAggregateType();

}
