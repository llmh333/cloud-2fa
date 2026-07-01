package org.example.cloud2fa.base;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestData<T> {

   private RestStatus status;
   private T data;
   private T message;
   private LocalDateTime timestamp;

   public RestData(T data) {
      this.data = data;
      this.status = RestStatus.SUCCESS;
      this.timestamp = LocalDateTime.now();
   }

   public static <T> RestData<T> error(T message) {
      return new RestData<>(RestStatus.ERROR, null, message, LocalDateTime.now());
   }

   public static <T> RestData<T> success(T data) {
      return new RestData<>(RestStatus.SUCCESS, data, null, LocalDateTime.now());
   }
}
