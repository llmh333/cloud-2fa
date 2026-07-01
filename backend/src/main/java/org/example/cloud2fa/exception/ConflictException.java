package org.example.cloud2fa.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

   public ConflictException(String message) {
      super(message, HttpStatus.CONFLICT);
   }

   public ConflictException(String message, String[] params) {
      super(message, HttpStatus.CONFLICT, params);
   }
}
