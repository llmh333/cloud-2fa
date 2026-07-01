package org.example.cloud2fa.utils;

import org.apache.commons.codec.binary.Base32;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;

public class TotpUtils {

   public static String generateTOTP(String secretKeyBase32, String algorithm, int period, int digits) {
      try {
         String secret = secretKeyBase32.trim().replace(" ", "").toUpperCase();

         Base32 base32 = new Base32();
         byte[] keyBytes = base32.decode(secret);

         long timeWindow = (period <= 0) ? 30 : period;
         long timeStep = System.currentTimeMillis() / 1000 / timeWindow;

         byte[] data = ByteBuffer.allocate(8).putLong(timeStep).array();

         String algoJava = "Hmac" + algorithm.replace("Hmac", ""); // Fix tên nếu cần
         Mac mac = Mac.getInstance(algoJava);
         mac.init(new SecretKeySpec(keyBytes, algoJava));
         byte[] hash = mac.doFinal(data);

         int offset = hash[hash.length - 1] & 0xF;
         long binary = ((hash[offset] & 0x7f) << 24) |
               ((hash[offset + 1] & 0xff) << 16) |
               ((hash[offset + 2] & 0xff) << 8) |
               (hash[offset + 3] & 0xff);

         long otp = binary % (long) Math.pow(10, digits);

         String format = "%0" + digits + "d";
         return String.format(format, otp);

      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}