package org.example.cloud2fa.domain.entity.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class DateAuditing {

   @CreatedDate
   @Column(updatable = false)
   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
   private LocalDateTime createdAt;

   @LastModifiedDate
   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
   private LocalDateTime updatedAt;
}
