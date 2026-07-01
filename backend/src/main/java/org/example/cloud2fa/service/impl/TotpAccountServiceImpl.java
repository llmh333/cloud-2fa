package org.example.cloud2fa.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.cloud2fa.constant.MessageKey;
import org.example.cloud2fa.constant.enums.DefaultAlgorithm;
import org.example.cloud2fa.domain.dto.request.AddSecretKeyRequestDto;
import org.example.cloud2fa.domain.dto.request.UpdateTotpAccountRequestDto;
import org.example.cloud2fa.domain.dto.response.TotpResponseDto;
import org.example.cloud2fa.domain.dto.response.TotpSyncDataDto;
import org.example.cloud2fa.domain.entity.TotpAccount;
import org.example.cloud2fa.domain.entity.User;
import org.example.cloud2fa.exception.ForbiddenException;
import org.example.cloud2fa.exception.NotFoundException;
import org.example.cloud2fa.repository.TotpAccountRepository;
import org.example.cloud2fa.repository.UserRepository;
import org.example.cloud2fa.security.UserPrincipal;
import org.example.cloud2fa.service.EncryptionService;
import org.example.cloud2fa.service.TotpAccountService;
import org.example.cloud2fa.utils.TotpUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TotpAccountServiceImpl implements TotpAccountService {

   private final EncryptionService encryptionService;
   private final UserRepository userRepository;
   private final TotpAccountRepository totpAccountRepository;
   private final PasswordEncoder passwordEncoder;

   @Override
   public boolean addSecretKey(AddSecretKeyRequestDto requestDto) {

      User user = checkCurrentUser(requestDto.getMasterPassword());

      String encryptedSecret = encryptionService.encrypt(requestDto.getSecretKey(), requestDto.getMasterPassword(),
            user.getEncryptionSalt());

      TotpAccount totpAccount = TotpAccount.builder()
            .user(user)
            .secretBlob(encryptedSecret)
            .accountName(user.getUsername())
            .issuer(requestDto.getIssuer())
            .digits(requestDto.getDigits())
            .period(requestDto.getPeriod())
            .algorithm(requestDto.getAlgorithm() != null ? requestDto.getAlgorithm() : DefaultAlgorithm.HmacSHA1.name())
            .build();
      totpAccountRepository.save(totpAccount);
      return true;
   }

   @Override
   public List<TotpResponseDto> generateTotp(String masterPassword) {
      User user = checkCurrentUser(masterPassword);

      List<TotpAccount> totpAccounts = totpAccountRepository.findByUserId(user.getId());

      List<TotpResponseDto> totpResponseDtoList = totpAccounts.stream().map(totpAccount -> {
         try {
            String originalSecret = encryptionService.decrypt(totpAccount.getSecretBlob(), masterPassword,
                  user.getEncryptionSalt());

            String otpCode = TotpUtils.generateTOTP(
                  originalSecret,
                  totpAccount.getAlgorithm(),
                  totpAccount.getPeriod(),
                  totpAccount.getDigits());

            TotpResponseDto totpResponseDto = TotpResponseDto.builder()
                  .id(totpAccount.getId())
                  .accountName(totpAccount.getAccountName())
                  .issuer(totpAccount.getIssuer())
                  .code(otpCode)
                  .period(totpAccount.getPeriod())
                  .remainingSeconds(
                        totpAccount.getPeriod() - (System.currentTimeMillis() / 1000) % totpAccount.getPeriod())
                  .build();
            return totpResponseDto;
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      }).collect(Collectors.toList());
      return totpResponseDtoList;
   }

   private User checkCurrentUser(String masterPassword) {
      UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
      Optional<User> userOptional = userRepository.findByUsername(userPrincipal.getUsername());
      if (userOptional.isEmpty()) {
         throw new NotFoundException(MessageKey.ErrorMessage.User.USERNAME_NOT_FOUND,
               new String[] { userPrincipal.getUsername() });
      }

      User user = userOptional.get();
      if (!passwordEncoder.matches(masterPassword, user.getMasterPassword())) {
         throw new NotFoundException(MessageKey.ErrorMessage.Valid.MASTER_PASSWORD);
      }
      return user;
   }

   @Override
   public List<TotpSyncDataDto> syncData(String masterPassword) {
      User user = checkCurrentUser(masterPassword);

      List<TotpAccount> totpAccounts = totpAccountRepository.findByUserId(user.getId());

      return totpAccounts.stream().map(totpAccount -> TotpSyncDataDto.builder()
            .id(totpAccount.getId())
            .accountName(totpAccount.getAccountName())
            .issuer(totpAccount.getIssuer())
            .secretBlob(totpAccount.getSecretBlob())
            .digits(totpAccount.getDigits())
            .period(totpAccount.getPeriod())
            .algorithm(totpAccount.getAlgorithm())
            .encryptionSalt(user.getEncryptionSalt())
            .build()).collect(Collectors.toList());
   }

   @Override
   public boolean updateTotpAccount(String id, UpdateTotpAccountRequestDto requestDto) {
      User user = checkCurrentUser(requestDto.getMasterPassword());

      TotpAccount totpAccount = getTotpAccountWithAuthorization(id, user);

      if (requestDto.getAccountName() != null) {
         totpAccount.setAccountName(requestDto.getAccountName());
      }
      if (requestDto.getIssuer() != null) {
         totpAccount.setIssuer(requestDto.getIssuer());
      }
      if (requestDto.getAlgorithm() != null) {
         totpAccount.setAlgorithm(requestDto.getAlgorithm());
      }
      if (requestDto.getDigits() != null) {
         totpAccount.setDigits(requestDto.getDigits());
      }
      if (requestDto.getPeriod() != null) {
         totpAccount.setPeriod(requestDto.getPeriod());
      }

      totpAccountRepository.save(totpAccount);
      return true;
   }

   @Override
   public boolean deleteTotpAccount(String id, String masterPassword) {
      User user = checkCurrentUser(masterPassword);

      TotpAccount totpAccount = getTotpAccountWithAuthorization(id, user);

      totpAccountRepository.delete(totpAccount);
      return true;
   }

   private TotpAccount getTotpAccountWithAuthorization(String id, User user) {
      Optional<TotpAccount> totpAccountOptional = totpAccountRepository.findById(id);
      if (totpAccountOptional.isEmpty()) {
         throw new NotFoundException(MessageKey.ErrorMessage.TotpAccount.NOT_FOUND, new String[] { id });
      }

      TotpAccount totpAccount = totpAccountOptional.get();

      if (!totpAccount.getUser().getId().equals(user.getId())) {
         throw new ForbiddenException(MessageKey.ErrorMessage.TotpAccount.FORBIDDEN);
      }

      return totpAccount;
   }
}
