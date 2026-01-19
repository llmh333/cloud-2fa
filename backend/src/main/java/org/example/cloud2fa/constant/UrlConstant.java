package org.example.cloud2fa.constant;

public class UrlConstant {
   public static final String API_VERSION_1 = "/api/v1";

   public static class Auth {
      public static final String PREFIX = "/auth";
      public static final String LOGIN = PREFIX + "/login";
      public static final String REGISTER = PREFIX + "/register";
      public static final String FORGOT_PASSWORD = PREFIX + "/forgot-password";
      public static final String VERIFY_FORGOT_PASSWORD = FORGOT_PASSWORD + "/verify";
      public static final String REFRESH_TOKEN = PREFIX + "/refresh-token";
   }
}
