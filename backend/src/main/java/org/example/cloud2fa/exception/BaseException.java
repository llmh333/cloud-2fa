package org.example.cloud2fa.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseException extends RuntimeException {

   private HttpStatus status;
   private String message;
   private String[] params;

   public BaseException(String message, HttpStatus status) {
      this.message = message;
      this.status = status;
   }

   public BaseException(String message, HttpStatus status, String[] params) {
      this.status = status;
      this.message = message;
      this.params = params;
   }
}
