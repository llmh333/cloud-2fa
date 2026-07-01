package org.example.cloud2fa.service.impl;

import java.security.SecureRandom;
import java.util.Base64;

import org.example.cloud2fa.constant.MessageKey;
import org.example.cloud2fa.constant.enums.AccountStatusEnum;
import org.example.cloud2fa.constant.enums.RoleEnum;
import org.example.cloud2fa.domain.dto.request.LoginRequestDto;
import org.example.cloud2fa.domain.dto.request.RegisterRequestDto;
import org.example.cloud2fa.domain.dto.response.TokenResponseDto;
import org.example.cloud2fa.domain.dto.response.UserResponeDto;
import org.example.cloud2fa.domain.entity.User;
import org.example.cloud2fa.domain.mapper.UserMapper;
import org.example.cloud2fa.exception.ConflictException;
import org.example.cloud2fa.exception.UnauthorizedException;
import org.example.cloud2fa.repository.UserRepository;
import org.example.cloud2fa.security.JwtTokenProvider;
import org.example.cloud2fa.security.UserPrincipal;
import org.example.cloud2fa.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final JwtTokenProvider jwtTokenProvider;
   private final UserMapper userMapper;

   @Override
   public TokenResponseDto login(LoginRequestDto requestDto) {
      User user = userRepository.findByUsername(requestDto.getUsername())
            .orElseThrow(() -> new UnauthorizedException(MessageKey.ErrorMessage.Auth.INVALID_CREDENTIALS));

      if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
         throw new UnauthorizedException(MessageKey.ErrorMessage.Auth.INVALID_CREDENTIALS);
      }

      UserPrincipal userPrincipal = UserPrincipal.create(user);

      String accessToken = jwtTokenProvider.generateToken(userPrincipal, false);
      String refreshToken = jwtTokenProvider.generateToken(userPrincipal, true);

      return TokenResponseDto.builder()
            .type("Bearer")
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .user(userMapper.toUserSummaryResponseDto(user))
            .build();
   }

   @Override
   public UserResponeDto register(RegisterRequestDto requestDto) {
      if (userRepository.existsByUsername(requestDto.getUsername())) {
         throw new ConflictException(MessageKey.ErrorMessage.User.USERNAME_EXISTS,
               new String[] { requestDto.getUsername() });
      }

      if (userRepository.existsByEmail(requestDto.getEmail())) {
         throw new ConflictException(MessageKey.ErrorMessage.User.EMAIL_EXISTS,
               new String[] { requestDto.getEmail() });
      }

      if (userRepository.existsByPhone(requestDto.getPhone())) {
         throw new ConflictException(MessageKey.ErrorMessage.User.PHONE_EXISTS,
               new String[] { requestDto.getPhone() });
      }

      byte[] salt = new byte[32];
      new SecureRandom().nextBytes(salt);

      User user = User.builder()
            .username(requestDto.getUsername())
            .password(passwordEncoder.encode(requestDto.getPassword()))
            .masterPassword(passwordEncoder.encode(requestDto.getMasterPassword()))
            .encryptionSalt(Base64.getEncoder().encodeToString(salt))
            .email(requestDto.getEmail())
            .phone(requestDto.getPhone())
            .role(RoleEnum.USER)
            .status(AccountStatusEnum.ACTIVE)
            .build();

      User savedUser = userRepository.save(user);

      return userMapper.toUserResponseDto(savedUser);
   }
}
