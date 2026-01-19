package org.example.cloud2fa.service;

import org.example.cloud2fa.domain.dto.request.LoginRequestDto;
import org.example.cloud2fa.domain.dto.request.RegisterRequestDto;
import org.example.cloud2fa.domain.dto.response.TokenResponseDto;
import org.example.cloud2fa.domain.dto.response.UserResponeDto;

public interface AuthService {
   TokenResponseDto login(LoginRequestDto requestDto);

   UserResponeDto register(RegisterRequestDto requestDto);
}
