package org.example.cloud2fa.application.usecase;

import org.example.cloud2fa.application.dto.request.CreateUserRequest;
import org.example.cloud2fa.application.dto.response.UserResponse;
import org.example.cloud2fa.application.mapper.UserMapper;
import org.example.cloud2fa.application.port.in.CreateUserUseCase;
import org.example.cloud2fa.application.port.out.DomainEventPublisher;
import org.example.cloud2fa.domain.event.UserEvent;
import org.example.cloud2fa.domain.exception.UserAlreadyExistsException;
import org.example.cloud2fa.domain.model.User;
import org.example.cloud2fa.domain.model.object.Role;
import org.example.cloud2fa.domain.repository.UserRepository;
import org.example.cloud2fa.domain.service.PasswordEncoder;
import org.example.cloud2fa.shared.constants.RoleEnums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateUserUseCaseImpl implements CreateUserUseCase {

   private final UserRepository userRepository;
   private final UserMapper userMapper;
   private final DomainEventPublisher eventPublisher;
   private final PasswordEncoder passwordEncoder;

   @Override
   public UserResponse execute(CreateUserRequest request) {
      validateUserDoesNotExist(request);

      String encodedPassword = passwordEncoder.encode(request.password());
      String encodedMasterPassword = passwordEncoder.encode(request.masterPassword());

      User user = User.create(
            request.username(),
            encodedPassword,
            encodedMasterPassword,
            request.email(),
            new Role(RoleEnums.USER));

      User savedUser = userRepository.save(user);

      eventPublisher.publish(new UserEvent.Created(
            savedUser.getIdValue(),
            savedUser.getUsernameValue(),
            savedUser.getEmailValue(),
            savedUser.getRoleValue().toString()));
      return userMapper.toResponse(savedUser);
   }

   private void validateUserDoesNotExist(CreateUserRequest request) {
      if (userRepository.existsByUsername(request.username())) {
         throw UserAlreadyExistsException.byUsername(request.username());
      }
      if (userRepository.existsByEmail(request.email())) {
         throw UserAlreadyExistsException.byEmail(request.email());
      }
   }
}
