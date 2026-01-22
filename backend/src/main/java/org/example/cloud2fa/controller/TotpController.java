package org.example.cloud2fa.controller;

import java.util.List;

import org.example.cloud2fa.base.ApiResponseUtil;
import org.example.cloud2fa.base.RestApiV1;
import org.example.cloud2fa.constant.UrlConstant;
import org.example.cloud2fa.domain.dto.request.AddSecretKeyRequestDto;
import org.example.cloud2fa.domain.dto.request.DeleteTotpAccountRequestDto;
import org.example.cloud2fa.domain.dto.request.GenerateTotpRequestDto;
import org.example.cloud2fa.domain.dto.request.UpdateTotpAccountRequestDto;
import org.example.cloud2fa.domain.dto.response.TotpResponseDto;
import org.example.cloud2fa.domain.dto.response.TotpSyncDataDto;
import org.example.cloud2fa.service.TotpAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestApiV1
@RequiredArgsConstructor
public class TotpController {

   private final TotpAccountService totpAccountService;

   @PostMapping(UrlConstant.Totp.ADD_SECRET_KEY)
   public ResponseEntity<?> addSecretKey(@Valid @RequestBody AddSecretKeyRequestDto requestDto) {
      boolean result = totpAccountService.addSecretKey(requestDto);
      return ApiResponseUtil.success(HttpStatus.CREATED, result);
   }

   @PostMapping(UrlConstant.Totp.GENERATE)
   public ResponseEntity<?> generateTotp(@Valid @RequestBody GenerateTotpRequestDto requestDto) {
      List<TotpResponseDto> response = totpAccountService.generateTotp(requestDto.getMasterPassword());
      return ApiResponseUtil.success(response);
   }

   @PostMapping(UrlConstant.Totp.SYNC_DATA)
   public ResponseEntity<?> syncData(@Valid @RequestBody GenerateTotpRequestDto requestDto) {
      List<TotpSyncDataDto> response = totpAccountService.syncData(requestDto.getMasterPassword());
      return ApiResponseUtil.success(response);
   }

   @PutMapping(UrlConstant.Totp.UPDATE)
   public ResponseEntity<?> updateTotpAccount(
         @PathVariable String id,
         @Valid @RequestBody UpdateTotpAccountRequestDto requestDto) {
      boolean result = totpAccountService.updateTotpAccount(id, requestDto);
      return ApiResponseUtil.success(result);
   }

   @DeleteMapping(UrlConstant.Totp.DELETE)
   public ResponseEntity<?> deleteTotpAccount(
         @PathVariable String id,
         @Valid @RequestBody DeleteTotpAccountRequestDto requestDto) {
      boolean result = totpAccountService.deleteTotpAccount(id, requestDto.getMasterPassword());
      return ApiResponseUtil.success(result);
   }
}
