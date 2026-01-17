package org.example.cloud2fa.domain.exception;

public class InvalidCredentialsException extends DomainException {

   public InvalidCredentialsException() {
      super("Invalid username or password");
   }

   public InvalidCredentialsException(String message) {
      super(message);
   }
}
