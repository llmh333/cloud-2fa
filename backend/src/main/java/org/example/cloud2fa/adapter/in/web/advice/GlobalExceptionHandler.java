package org.example.cloud2fa.adapter.in.web.advice;

import org.example.cloud2fa.adapter.in.web.response.ApiResponse;
import org.example.cloud2fa.domain.exception.DomainException;
import org.example.cloud2fa.domain.exception.InvalidCredentialsException;
import org.example.cloud2fa.domain.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(InvalidCredentialsException.class)
   public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
      log.warn("Invalid credentials: {}", ex.getMessage());
      return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.error(ex.getMessage()));
   }

   @ExceptionHandler(UserAlreadyExistsException.class)
   public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
      log.warn("User already exists: {}", ex.getMessage());
      return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(ex.getMessage()));
   }

   @ExceptionHandler(DomainException.class)
   public ResponseEntity<ApiResponse<Void>> handleDomainException(DomainException ex) {
      log.warn("Domain exception: {}", ex.getMessage());
      return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(ex.getMessage()));
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
      log.error("Unexpected error: ", ex);
      return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("An unexpected error occurred"));
   }
}
