package org.example.cloud2fa.service;

public interface EncryptionService {

   /**
    * Encrypt a secret using AES encryption with user's master password
    * 
    * @param rawSecret           the plain text secret to encrypt
    * @param rawMasterPassword   the user's master password for key derivation
    * @param encryptedSaltBase64 the salt used for key derivation (Base64 encoded)
    * @return the encrypted secret (Base64 encoded)
    */
   String encrypt(String rawSecret, String rawMasterPassword, String encryptedSaltBase64);

   /**
    * Decrypt an encrypted secret using AES decryption with user's master password
    * 
    * @param encryptedSecret     the encrypted secret to decrypt (Base64 encoded)
    * @param rawMasterPassword   the user's master password for key derivation
    * @param encryptedSaltBase64 the salt used for key derivation (Base64 encoded)
    * @return the decrypted plain text secret
    */
   String decrypt(String encryptedSecret, String rawMasterPassword, String encryptedSaltBase64);
}
