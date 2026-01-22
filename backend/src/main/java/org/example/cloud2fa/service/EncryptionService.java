package org.example.cloud2fa.service;

public interface EncryptionService {
   public String encrypt(String rawSecret, String rawMasterPassword, byte[] salt);

   public String decrypt(String encryptedSecret, String rawMasterPassword, String encryptedSaltBase64);
}
