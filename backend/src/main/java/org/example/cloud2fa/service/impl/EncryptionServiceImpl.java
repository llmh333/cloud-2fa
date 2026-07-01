package org.example.cloud2fa.service.impl;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.example.cloud2fa.service.EncryptionService;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {
   private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
   private static final int TAG_LENGTH_BIT = 128; // Độ dài thẻ xác thực
   private static final int IV_LENGTH_BYTE = 12; // Độ dài IV chuẩn GCM
   private static final int AES_KEY_BIT = 256; // AES 256 bit

   private static final String KDF_ALGO = "PBKDF2WithHmacSHA256";
   private static final int ITERATION_COUNT = 600_000; // Số vòng lặp để chống dò pass

   private final SecureRandom secureRandom = new SecureRandom();

   @Override
   public String encrypt(String rawSecret, String rawMasterPassword, String encryptedSaltBase64) {
      try {
         byte[] decodedBytes = Base64.getDecoder().decode(encryptedSaltBase64);

         byte[] iv = new byte[IV_LENGTH_BYTE];
         secureRandom.nextBytes(iv);

         SecretKey secretKey = generateSecretKey(rawMasterPassword, decodedBytes);

         Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
         cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

         byte[] cipherText = cipher.doFinal(rawSecret.getBytes(StandardCharsets.UTF_8));

         ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
         byteBuffer.put(iv);
         byteBuffer.put(cipherText);

         return Base64.getEncoder().encodeToString(byteBuffer.array());

      } catch (GeneralSecurityException e) {
         throw new RuntimeException("Lỗi mã hóa: " + e.getMessage(), e);
      }
   }

   @Override
   public String decrypt(String encryptedSecret, String rawMasterPassword, String encryptedSaltBase64) {
      try {
         byte[] saltBytes = Base64.getDecoder().decode(encryptedSaltBase64);

         byte[] encryptedSecretBytes = Base64.getDecoder().decode(encryptedSecret);

         ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedSecretBytes);

         byte[] iv = new byte[IV_LENGTH_BYTE];
         byteBuffer.get(iv);

         byte[] cipherText = new byte[byteBuffer.remaining()];
         byteBuffer.get(cipherText);

         SecretKey secretKey = generateSecretKey(rawMasterPassword, saltBytes);

         Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
         cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

         byte[] plainText = cipher.doFinal(cipherText);

         return new String(plainText, StandardCharsets.UTF_8);

      } catch (GeneralSecurityException e) {
         throw new RuntimeException("Giải mã thất bại. Có thể sai Master Password hoặc dữ liệu hỏng.", e);
      }
   }

   private SecretKey generateSecretKey(String password, byte[] salt) throws GeneralSecurityException {
      SecretKeyFactory factory = SecretKeyFactory.getInstance(KDF_ALGO);
      KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, AES_KEY_BIT);
      SecretKey tmp = factory.generateSecret(spec);
      return new SecretKeySpec(tmp.getEncoded(), "AES");
   }

}
