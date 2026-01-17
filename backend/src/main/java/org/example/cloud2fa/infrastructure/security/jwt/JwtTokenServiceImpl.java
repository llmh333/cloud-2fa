package org.example.cloud2fa.infrastructure.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.example.cloud2fa.domain.model.User;
import org.example.cloud2fa.domain.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

   @Value("${jwt.secret}")
   private String jwtSecret;

   @Value("${jwt.expiration.access}")
   private long jwtExpirationAccess;

   @Value("${jwt.expiration.refresh}")
   private long jwtExpirationRefresh;

   @Override
   public String generateToken(User user, boolean isRefreshToken) {
      Date now = new Date();
      Date expiryDate;
      if (isRefreshToken) {
         expiryDate = new Date(now.getTime() + jwtExpirationRefresh);
      } else {
         expiryDate = new Date(now.getTime() + jwtExpirationAccess);
      }

      Map<String, Object> claims = new HashMap<>();
      claims.put("role", user.getRoleValue().toString());
      claims.put("email", user.getEmailValue());
      claims.put("username", user.getUsernameValue());

      return Jwts.builder()
            .subject(user.getIdValue())
            .claims(claims)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact();
   }

   @Override
   public String extractUserId(String token) {
      return getClaims(token).getSubject();
   }

   @Override
   public String extractUsername(String token) {
      return getClaims(token).get("username", String.class);
   }

   @Override
   public boolean isTokenValid(String token) {
      try {
         Claims claims = getClaims(token);
         return !claims.getExpiration().before(new Date());
      } catch (JwtException | IllegalArgumentException e) {
         return false;
      }
   }

   @Override
   public long getExpirationTime() {
      return jwtExpirationAccess;
   }

   private Claims getClaims(String token) {
      return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
   }

   private SecretKey getSigningKey() {
      byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
      return Keys.hmacShaKeyFor(keyBytes);
   }
}
