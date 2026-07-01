package org.example.cloud2fa.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.example.cloud2fa.constant.enums.AccountStatusEnum;
import org.example.cloud2fa.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrincipal implements UserDetails {

   private String username;
   private String password;
   private String email;
   private String phone;
   private AccountStatusEnum status;
   private Set<GrantedAuthority> authorities;

   public UserPrincipal(User user) {
      this.username = user.getUsername();
      this.password = user.getPassword();
      this.email = user.getEmail();
      this.phone = user.getPhone();
      this.status = user.getStatus();
      this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
   }

   public static UserPrincipal create(User user) {
      return new UserPrincipal(user);
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
   }

   @Override
   public String getPassword() {
      return password;
   }

   @Override
   public String getUsername() {
      return username;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return status == AccountStatusEnum.ACTIVE;
   }
}
