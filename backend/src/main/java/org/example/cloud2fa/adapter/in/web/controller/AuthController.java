package org.example.cloud2fa.adapter.in.web.controller;

import org.example.cloud2fa.adapter.in.web.request.CreateUserWebRequest;
import org.example.cloud2fa.adapter.in.web.request.LoginWebRequest;
import org.example.cloud2fa.adapter.in.web.response.ApiResponse;
import org.example.cloud2fa.application.dto.request.CreateUserRequest;
import org.example.cloud2fa.application.dto.request.LoginRequest;
import org.example.cloud2fa.application.dto.response.LoginResponse;
import org.example.cloud2fa.application.dto.response.UserResponse;
import org.example.cloud2fa.application.port.in.CreateUserUseCase;
import org.example.cloud2fa.application.port.in.LoginUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

   private final CreateUserUseCase createUserUseCase;
   private final LoginUseCase loginUseCase;

   @PostMapping("/register")
   public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody CreateUserWebRequest request) {
      CreateUserRequest useCaseRequest = new CreateUserRequest(
            request.username(),
            request.password(),
            request.masterPassword(),
            request.email());

      UserResponse response = createUserUseCase.execute(useCaseRequest);

      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("User registered successfully", response));
   }

   @PostMapping("/login")
   public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginWebRequest request) {
      LoginRequest useCaseRequest = new LoginRequest(
            request.username(),
            request.password());

      LoginResponse response = loginUseCase.execute(useCaseRequest);

      return ResponseEntity
            .ok(ApiResponse.success("Login successful", response));
   }
}
