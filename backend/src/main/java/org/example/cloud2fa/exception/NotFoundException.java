package org.example.cloud2fa.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

   public NotFoundException(String message) {
      super(message, HttpStatus.NOT_FOUND);
   }

   public NotFoundException(String message, String[] params) {
      super(message, HttpStatus.NOT_FOUND, params);
   }
}
