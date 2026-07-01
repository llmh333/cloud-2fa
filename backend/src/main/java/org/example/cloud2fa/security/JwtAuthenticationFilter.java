package org.example.cloud2fa.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.example.cloud2fa.base.RestData;
import org.example.cloud2fa.constant.MessageKey.ErrorMessage;
import org.example.cloud2fa.service.impl.CustomUserDetailsService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   private final JwtTokenProvider jwtTokenProvider;
   private final CustomUserDetailsService customUserDetailsService;
   private final MessageSource messageSource;
   private final ObjectMapper objectMapper;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {
      String token = extractTokenFromRequest(request);
      try {
         if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getSubject(token);
            UserPrincipal userDetails = (UserPrincipal) customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
         }
         filterChain.doFilter(request, response);
      } catch (Exception e) {
         handleAuthenticationError(request, response, e);
      }
   }

   private String extractTokenFromRequest(HttpServletRequest request) {
      String authHeader = request.getHeader("Authorization");

      if (authHeader != null && authHeader.startsWith("Bearer ")) {
         return authHeader.substring("Bearer ".length());
      }

      return null;
   }

   private void handleAuthenticationError(HttpServletRequest request, HttpServletResponse response, Exception e)
         throws IOException {
      String errorMessage = messageSource.getMessage(
            ErrorMessage.Auth.INVALID_CREDENTIALS,
            null,
            request.getLocale());

      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());

      RestData<?> restData = RestData.error(errorMessage);
      String jsonResponse = objectMapper.writeValueAsString(restData);

      response.getOutputStream().write(jsonResponse.getBytes(StandardCharsets.UTF_8));
   }
}
