package org.example.cloud2fa.application.port.in;

import org.example.cloud2fa.application.dto.request.LoginRequest;
import org.example.cloud2fa.application.dto.response.LoginResponse;

public interface LoginUseCase {

   LoginResponse execute(LoginRequest request);
}
