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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication and registration endpoints")
public class AuthController {

   private final AuthService authService;

   @Operation(summary = "Register a new user", description = "Creates a new user account with email, password, and master password. "
         +
         "The master password is used for encrypting TOTP secrets.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponeDto.class))),
         @ApiResponse(responseCode = "400", description = "Invalid request data (validation error)", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "409", description = "Email already exists", content = @Content(mediaType = "application/json"))
   })
   @PostMapping(UrlConstant.Auth.REGISTER)
   public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto requestDto) {
      UserResponeDto response = authService.register(requestDto);
      return ApiResponseUtil.success(HttpStatus.CREATED, response);
   }

   @Operation(summary = "User login", description = "Authenticates user with email and password. " +
         "Returns a JWT access token for subsequent API calls.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponseDto.class))),
         @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = "application/json"))
   })
   @PostMapping(UrlConstant.Auth.LOGIN)
   public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
      TokenResponseDto response = authService.login(requestDto);
      return ApiResponseUtil.success(response);
   }
}
