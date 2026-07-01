package org.example.cloud2fa.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {

   public ForbiddenException(String message) {
      super(message, HttpStatus.FORBIDDEN);
   }

   public ForbiddenException(String message, String[] params) {
      super(message, HttpStatus.FORBIDDEN, params);
   }
}
