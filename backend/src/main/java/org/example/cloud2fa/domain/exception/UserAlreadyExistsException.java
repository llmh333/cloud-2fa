package org.example.cloud2fa.domain.exception;

public class UserAlreadyExistsException extends DomainException {

   public UserAlreadyExistsException(String field, String value) {
      super(String.format("User with %s '%s' already exists", field, value));
   }

   public static UserAlreadyExistsException byUsername(String username) {
      return new UserAlreadyExistsException("username", username);
   }

   public static UserAlreadyExistsException byEmail(String email) {
      return new UserAlreadyExistsException("email", email);
   }
}
