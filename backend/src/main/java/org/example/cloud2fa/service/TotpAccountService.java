package org.example.cloud2fa.service;

import java.util.List;

import org.example.cloud2fa.domain.dto.request.AddSecretKeyRequestDto;
import org.example.cloud2fa.domain.dto.request.UpdateTotpAccountRequestDto;
import org.example.cloud2fa.domain.dto.response.TotpResponseDto;
import org.example.cloud2fa.domain.dto.response.TotpSyncDataDto;

public interface TotpAccountService {

   /**
    * Add a new TOTP secret key for the current user
    * 
    * @param requestDto contains secret key, issuer, and other TOTP configuration
    * @return true if the secret key was added successfully
    */
   boolean addSecretKey(AddSecretKeyRequestDto requestDto);

   /**
    * Generate TOTP codes for all accounts of the current user
    * 
    * @param masterPassword the user's master password for decryption
    * @return list of TOTP codes with account information
    */
   List<TotpResponseDto> generateTotp(String masterPassword);

   /**
    * Sync TOTP account data for offline usage
    * 
    * @param masterPassword the user's master password for authentication
    * @return list of TOTP account data including encrypted secrets
    */
   List<TotpSyncDataDto> syncData(String masterPassword);

   /**
    * Update a TOTP account
    * 
    * @param id         the TOTP account ID to update
    * @param requestDto contains fields to update (accountName, issuer, algorithm,
    *                   digits, period)
    * @return true if the account was updated successfully
    * @throws NotFoundException  if the account is not found
    * @throws ForbiddenException if the user doesn't own this account
    */
   boolean updateTotpAccount(String id, UpdateTotpAccountRequestDto requestDto);

   /**
    * Delete a TOTP account
    * 
    * @param id             the TOTP account ID to delete
    * @param masterPassword the user's master password for authentication
    * @return true if the account was deleted successfully
    * @throws NotFoundException  if the account is not found
    * @throws ForbiddenException if the user doesn't own this account
    */
   boolean deleteTotpAccount(String id, String masterPassword);
}
