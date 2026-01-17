package org.example.cloud2fa.infrastructure.persistence.mapper;

import org.example.cloud2fa.domain.model.User;
import org.example.cloud2fa.domain.model.object.Email;
import org.example.cloud2fa.domain.model.object.Password;
import org.example.cloud2fa.domain.model.object.Role;
import org.example.cloud2fa.domain.model.object.UserId;
import org.example.cloud2fa.domain.model.object.Username;
import org.example.cloud2fa.infrastructure.persistence.entity.UserEntity;
import org.example.cloud2fa.shared.constants.RoleEnums;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

   public UserEntity toEntity(User user) {
      return UserEntity.builder()
            .id(user.getIdValue())
            .username(user.getUsernameValue())
            .password(user.getPasswordValue())
            .masterPassword(user.getMasterPasswordValue())
            .email(user.getEmailValue())
            .role(user.getRoleValue().toString())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
   }

   public User toDomain(UserEntity entity) {
      return new User(
            new UserId(entity.getId()),
            new Username(entity.getUsername()),
            new Password(entity.getPassword()),
            new Password(entity.getMasterPassword()),
            new Email(entity.getEmail()),
            new Role(RoleEnums.valueOf(entity.getRole())),
            entity.getCreatedAt(),
            entity.getUpdatedAt());
   }
}
