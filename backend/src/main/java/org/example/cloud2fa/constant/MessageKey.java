package org.example.cloud2fa.constant;

public class MessageKey {
   public static class SuccessMessage {
      public static class Auth {
         public static final String REGISTER = "auth.register.success";
         public static final String LOGIN = "auth.login.success";
      }
   }

   public static class ErrorMessage {
      public static class Auth {
         public static final String INVALID_CREDENTIALS = "auth.invalid.credentials";
      }

      public static class User {
         public static final String USERNAME_NOT_FOUND = "user.username.not.found";
         public static final String USERNAME_EXISTS = "user.username.exists";
         public static final String EMAIL_EXISTS = "user.email.exists";
         public static final String PHONE_EXISTS = "user.phone.exists";
      }
   }
}
