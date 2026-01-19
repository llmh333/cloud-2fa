package org.example.cloud2fa.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.example.cloud2fa.base.ApiResponseUtil;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptoionHandler {
   private final MessageSource messageSource;

   @ExceptionHandler(ConstraintViolationException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
      Map<String, String> result = new LinkedHashMap<>();
      ex.getConstraintViolations().forEach((error) -> {
         String fieldName = ((PathImpl) error.getPropertyPath()).getLeafNode().getName();
         String errorMessage = messageSource.getMessage(Objects.requireNonNull(error.getMessage()), null,
               LocaleContextHolder.getLocale());
         result.put(fieldName, errorMessage);
      });
      return ApiResponseUtil.error(HttpStatus.BAD_REQUEST, result);
   }

   @ExceptionHandler(BaseException.class)
   public ResponseEntity<?> handleBaseException(BaseException e) {
      log.error("BaseException: {}", e.getMessage());
      String message = messageSource.getMessage(e.getMessage(), e.getParams(), LocaleContextHolder.getLocale());
      return ApiResponseUtil.error(e.getStatus(), message);
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<?> handleException(Exception e) {
      log.error("Exception: {}", e.getMessage());
      e.printStackTrace();
      String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
      return ApiResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message);
   }
}
