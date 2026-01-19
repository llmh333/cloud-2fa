package org.example.cloud2fa.service.impl;

import org.example.cloud2fa.constant.MessageKey;
import org.example.cloud2fa.domain.entity.User;
import org.example.cloud2fa.domain.repository.UserRepository;
import org.example.cloud2fa.exception.NotFoundException;
import org.example.cloud2fa.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

      private final UserRepository userRepository;

      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException(MessageKey.ErrorMessage.User.USERNAME_NOT_FOUND,
                                    new String[] { username }));

            return UserPrincipal.create(user);
      }
}
