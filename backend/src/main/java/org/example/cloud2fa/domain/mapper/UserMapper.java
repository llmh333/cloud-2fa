package org.example.cloud2fa.domain.mapper;

import org.example.cloud2fa.domain.dto.response.UserResponeDto;
import org.example.cloud2fa.domain.dto.response.UserSummaryResponseDto;
import org.example.cloud2fa.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

   public UserResponeDto toUserResponseDto(User user) {
      return UserResponeDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .phone(user.getPhone())
            .role(user.getRole().name())
            .status(user.getStatus().name())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
   }

   public UserSummaryResponseDto toUserSummaryResponseDto(User user) {
      return UserSummaryResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .phone(user.getPhone())
            .role(user.getRole().name())
            .status(user.getStatus().name())
            .build();
   }
}
