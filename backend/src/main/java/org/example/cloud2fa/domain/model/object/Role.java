package org.example.cloud2fa.domain.model.object;

import java.util.Objects;

import org.example.cloud2fa.shared.constants.RoleEnums;

public record Role(RoleEnums role) {
   public Role {
      Objects.requireNonNull(role, "Role cannot be null");
   }

   @Override
   public String toString() {
      return role.name();
   }
}
