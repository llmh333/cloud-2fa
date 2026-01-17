package org.example.cloud2fa.adapter.in.web.controller;

import org.example.cloud2fa.adapter.in.web.request.CreateUserWebRequest;
import org.example.cloud2fa.adapter.in.web.response.ApiResponse;
import org.example.cloud2fa.application.dto.request.CreateUserRequest;
import org.example.cloud2fa.application.dto.response.UserResponse;
import org.example.cloud2fa.application.port.in.CreateUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * REST Controller for User operations.
 * This is the "driving adapter" - it drives the application via use cases.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

      private final CreateUserUseCase createUserUseCase;

      @PostMapping
      public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody CreateUserWebRequest request) {
            CreateUserRequest useCaseRequest = new CreateUserRequest(
                        request.username(),
                        request.password(),
                        request.masterPassword(),
                        request.email());

            UserResponse response = createUserUseCase.execute(useCaseRequest);

            return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(ApiResponse.success("User created successfully", response));
      }
}
