package org.example.cloud2fa.infrastructure.security.password;

import org.example.cloud2fa.domain.service.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderImpl implements PasswordEncoder {

   private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

   @Override
   public String encode(String plainPassword) {
      return bCryptEncoder.encode(plainPassword);
   }

   @Override
   public boolean matches(String plainPassword, String encodedPassword) {
      return bCryptEncoder.matches(plainPassword, encodedPassword);
   }
}
