package org.example.cloud2fa.domain.service;

public interface PasswordEncoder {
   String encode(String plainPassword);

   boolean matches(String plainPassword, String encodedPassword);
}
