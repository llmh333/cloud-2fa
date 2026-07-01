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

      public static class Valid {
         public static final String USERNAME = "invalid.user.username";
         public static final String PASSWORD = "invalid.user.password";
         public static final String MASTER_PASSWORD = "invalid.user.master.password";
         public static final String EMAIL = "invalid.user.email";
         public static final String PHONE = "invalid.user.phone";

         public static final String NOT_BLANK = "validation.not.blank";
         public static final String MIN = "validation.min";
      }

      public static class TotpAccount {
         public static final String NOT_FOUND = "totp.account.not.found";
         public static final String FORBIDDEN = "totp.account.forbidden";
      }
   }
}
