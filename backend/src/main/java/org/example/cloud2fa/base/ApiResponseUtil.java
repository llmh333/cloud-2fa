package org.example.cloud2fa.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {
   public static ResponseEntity<?> success(Object data) {
      return success(HttpStatus.OK, data);
   }

   public static ResponseEntity<?> success(HttpStatus status, Object data) {
      RestData<?> restData = RestData.success(data);
      return new ResponseEntity<>(restData, status);
   }

   public static ResponseEntity<?> error(HttpStatus status, Object message) {
      RestData<?> restData = RestData.error(message);
      return new ResponseEntity<>(restData, status);
   }
}
