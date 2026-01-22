package org.example.cloud2fa.service;

import org.example.cloud2fa.domain.dto.request.LoginRequestDto;
import org.example.cloud2fa.domain.dto.request.RegisterRequestDto;
import org.example.cloud2fa.domain.dto.response.TokenResponseDto;
import org.example.cloud2fa.domain.dto.response.UserResponeDto;

public interface AuthService {

   /**
    * Authenticate a user and generate access token
    * 
    * @param requestDto contains email and password for authentication
    * @return token response containing access token and expiration info
    * @throws UnauthorizedException if credentials are invalid
    */
   TokenResponseDto login(LoginRequestDto requestDto);

   /**
    * Register a new user account
    * 
    * @param requestDto contains user registration information (email, password,
    *                   master password)
    * @return user response containing created user information
    * @throws ConflictException if email already exists
    */
   UserResponeDto register(RegisterRequestDto requestDto);
}
