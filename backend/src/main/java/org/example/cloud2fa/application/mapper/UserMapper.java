package org.example.cloud2fa.application.mapper;

import org.example.cloud2fa.application.dto.response.UserResponse;
import org.example.cloud2fa.domain.model.User;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between User domain model and DTOs.
 */
@Component
public class UserMapper {

   public UserResponse toResponse(User user) {
      return new UserResponse(
            user.getIdValue(),
            user.getUsernameValue(),
            user.getEmailValue(),
            user.getCreatedAt());
   }
}
