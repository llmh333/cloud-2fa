package org.example.cloud2fa.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

   @Value("${JWT_SECRET}")
   private String jwtSecret;

   @Value("${JWT_EXPIRATION_ACCESS}")
   private long expiredAccessToken;

   @Value("${JWT_EXPIRATION_REFRESH}")
   private long expiredRefreshToken;

   public SecretKey getSecretKey() {
      return Keys.hmacShaKeyFor(jwtSecret.getBytes());
   }

   public String generateToken(UserPrincipal userPrincipal, boolean isRefresh) {

      Date currentDate = new Date();

      Date expireDate = new Date();
      if (isRefresh) {
         expireDate.setTime(currentDate.getTime() + expiredRefreshToken);
      } else {
         expireDate.setTime(currentDate.getTime() + expiredAccessToken);
      }

      Map<String, Object> claims = new HashMap<>();
      claims.put("email", userPrincipal.getEmail());
      claims.put("role", userPrincipal.getAuthorities());
      String token = Jwts.builder()
            .claims(claims)
            .subject(userPrincipal.getUsername())
            .issuedAt(new Date())
            .expiration(expireDate)
            .signWith(getSecretKey())
            .compact();
      return token;
   }

   public String getSubject(String token) {
      return Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
   }

   // validate Jwt token
   public boolean validateToken(String token) {
      try {
         Jwts.parser()
               .verifyWith(getSecretKey())
               .build()
               .parse(token);
         return true;
      } catch (MalformedJwtException e) {
         log.error("Invalid JWT token: {}", e.getMessage());
      } catch (ExpiredJwtException e) {
         log.error("JWT token is expired: {}", e.getMessage());
      } catch (UnsupportedJwtException e) {
         log.error("JWT token is unsupported: {}", e.getMessage());
      } catch (IllegalArgumentException e) {
         log.error("JWT claims string is empty: {}", e.getMessage());
      }
      return false;
   }
}
