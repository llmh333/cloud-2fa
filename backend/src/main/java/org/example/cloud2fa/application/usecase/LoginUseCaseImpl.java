package org.example.cloud2fa.application.usecase;

import org.example.cloud2fa.application.dto.request.LoginRequest;
import org.example.cloud2fa.application.dto.response.LoginResponse;
import org.example.cloud2fa.application.mapper.UserMapper;
import org.example.cloud2fa.application.port.in.LoginUseCase;
import org.example.cloud2fa.domain.exception.InvalidCredentialsException;
import org.example.cloud2fa.domain.model.User;
import org.example.cloud2fa.domain.repository.UserRepository;
import org.example.cloud2fa.domain.service.JwtTokenService;
import org.example.cloud2fa.domain.service.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

      private final UserRepository userRepository;
      private final PasswordEncoder passwordEncoder;
      private final JwtTokenService jwtTokenService;
      private final UserMapper userMapper;

      @Override
      public LoginResponse execute(LoginRequest request) {
            User user = userRepository.findByUsername(request.username())
                        .orElseThrow(InvalidCredentialsException::new);

            if (!passwordEncoder.matches(request.password(), user.getPasswordValue())) {
                  throw new InvalidCredentialsException();
            }

            String accessToken = jwtTokenService.generateToken(user, false);
            String refreshToken = jwtTokenService.generateToken(user, true);

            return LoginResponse.of(
                        accessToken,
                        refreshToken,
                        userMapper.toResponse(user));
      }
}
