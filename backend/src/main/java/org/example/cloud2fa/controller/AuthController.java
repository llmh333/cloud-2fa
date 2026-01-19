package org.example.cloud2fa.controller;

import org.example.cloud2fa.base.ApiResponseUtil;
import org.example.cloud2fa.base.RestApiV1;
import org.example.cloud2fa.base.RestData;
import org.example.cloud2fa.constant.UrlConstant;
import org.example.cloud2fa.domain.dto.request.LoginRequestDto;
import org.example.cloud2fa.domain.dto.request.RegisterRequestDto;
import org.example.cloud2fa.domain.dto.response.TokenResponseDto;
import org.example.cloud2fa.domain.dto.response.UserResponeDto;
import org.example.cloud2fa.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestApiV1
@RequiredArgsConstructor
public class AuthController {

   private final AuthService authService;

   @PostMapping(UrlConstant.Auth.REGISTER)
   public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto requestDto) {
      UserResponeDto response = authService.register(requestDto);
      return ApiResponseUtil.success(response);
   }

   @PostMapping(UrlConstant.Auth.LOGIN)
   public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
      TokenResponseDto response = authService.login(requestDto);
      return ApiResponseUtil.success(response);
   }
}
