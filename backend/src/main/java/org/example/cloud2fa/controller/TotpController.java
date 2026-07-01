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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "TOTP Management", description = "TOTP account management and code generation endpoints")
@SecurityRequirement(name = "bearerAuth")
public class TotpController {

   private final TotpAccountService totpAccountService;

   @Operation(summary = "Add a new TOTP account", description = "Creates a new TOTP account with the provided secret key and configuration. "
         +
         "The secret key will be encrypted using the user's master password before storage.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "201", description = "TOTP account created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
         @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content(mediaType = "application/json"))
   })
   @PostMapping(UrlConstant.Totp.ADD_SECRET_KEY)
   public ResponseEntity<?> addSecretKey(@Valid @RequestBody AddSecretKeyRequestDto requestDto) {
      boolean result = totpAccountService.addSecretKey(requestDto);
      return ApiResponseUtil.success(HttpStatus.CREATED, result);
   }

   @Operation(summary = "Generate TOTP codes", description = "Generates current TOTP codes for all accounts of the authenticated user. "
         +
         "Requires master password for decrypting stored secrets.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "TOTP codes generated successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TotpResponseDto.class)))),
         @ApiResponse(responseCode = "400", description = "Invalid master password", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content(mediaType = "application/json"))
   })
   @PostMapping(UrlConstant.Totp.GENERATE)
   public ResponseEntity<?> generateTotp(@Valid @RequestBody GenerateTotpRequestDto requestDto) {
      List<TotpResponseDto> response = totpAccountService.generateTotp(requestDto.getMasterPassword());
      return ApiResponseUtil.success(response);
   }

   @Operation(summary = "Sync TOTP data for offline usage", description = "Retrieves all TOTP account data including encrypted secrets for offline storage. "
         +
         "The client can use this data to generate TOTP codes offline.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Data synced successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TotpSyncDataDto.class)))),
         @ApiResponse(responseCode = "400", description = "Invalid master password", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content(mediaType = "application/json"))
   })
   @PostMapping(UrlConstant.Totp.SYNC_DATA)
   public ResponseEntity<?> syncData(@Valid @RequestBody GenerateTotpRequestDto requestDto) {
      List<TotpSyncDataDto> response = totpAccountService.syncData(requestDto.getMasterPassword());
      return ApiResponseUtil.success(response);
   }

   @Operation(summary = "Update a TOTP account", description = "Updates an existing TOTP account's metadata such as account name, issuer, "
         +
         "algorithm, digits, and period. The secret key cannot be changed.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "TOTP account updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
         @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "403", description = "Forbidden - User does not own this account", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "404", description = "TOTP account not found", content = @Content(mediaType = "application/json"))
   })
   @PutMapping(UrlConstant.Totp.UPDATE)
   public ResponseEntity<?> updateTotpAccount(
         @Parameter(description = "TOTP account ID", required = true) @PathVariable String id,
         @Valid @RequestBody UpdateTotpAccountRequestDto requestDto) {
      boolean result = totpAccountService.updateTotpAccount(id, requestDto);
      return ApiResponseUtil.success(result);
   }

   @Operation(summary = "Delete a TOTP account", description = "Permanently deletes a TOTP account. Requires master password verification "
         +
         "to ensure the user has proper authorization.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "TOTP account deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
         @ApiResponse(responseCode = "400", description = "Invalid master password", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "403", description = "Forbidden - User does not own this account", content = @Content(mediaType = "application/json")),
         @ApiResponse(responseCode = "404", description = "TOTP account not found", content = @Content(mediaType = "application/json"))
   })
   @DeleteMapping(UrlConstant.Totp.DELETE)
   public ResponseEntity<?> deleteTotpAccount(
         @Parameter(description = "TOTP account ID", required = true) @PathVariable String id,
         @Valid @RequestBody DeleteTotpAccountRequestDto requestDto) {
      boolean result = totpAccountService.deleteTotpAccount(id, requestDto.getMasterPassword());
      return ApiResponseUtil.success(result);
   }
}
