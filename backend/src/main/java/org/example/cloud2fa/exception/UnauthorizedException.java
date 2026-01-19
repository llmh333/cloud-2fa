package org.example.cloud2fa.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {

   public UnauthorizedException(String message) {
      super(message, HttpStatus.UNAUTHORIZED);
   }

   public UnauthorizedException(String message, String[] params) {
      super(message, HttpStatus.UNAUTHORIZED, params);
   }
}
