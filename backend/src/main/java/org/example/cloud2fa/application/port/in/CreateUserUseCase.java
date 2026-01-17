package org.example.cloud2fa.application.port.in;

import org.example.cloud2fa.application.dto.request.CreateUserRequest;
import org.example.cloud2fa.application.dto.response.UserResponse;

/**
 * Input port (interface) for Create User use case.
 * This defines WHAT the application can do, not HOW.
 */
public interface CreateUserUseCase {

   /**
    * Creates a new user in the system.
    *
    * @param request the user creation request
    * @return the created user response
    * @throws org.example.cloud2fa.domain.exception.UserAlreadyExistsException if
    *                                                                          username/email
    *                                                                          already
    *                                                                          exists
    */
   UserResponse execute(CreateUserRequest request);
}
