// utils/crypto.js

// Hàm phụ trợ: Chuyển Base64 thành Uint8Array (Byte array)
const base64ToBytes = (base64) => {
   const binaryString = atob(base64);
   const bytes = new Uint8Array(binaryString.length);
   for (let i = 0; i < binaryString.length; i++) {
      bytes[i] = binaryString.charCodeAt(i);
   }
   return bytes;
};

export const decryptSecret = async (encryptedBlobBase64, masterPassword, saltBase64) => {
   try {
      const enc = new TextEncoder();

      // 1. Chuẩn bị dữ liệu
      const saltBytes = base64ToBytes(saltBase64);
      const blobBytes = base64ToBytes(encryptedBlobBase64);

      // 2. Tách IV (12 byte đầu) và CipherText (phần còn lại)
      // Java: ByteBuffer.put(iv); ByteBuffer.put(cipherText);
      const iv = blobBytes.slice(0, 12);
      const cipherText = blobBytes.slice(12);

      // 3. Import Password vào Web Crypto
      const keyMaterial = await window.crypto.subtle.importKey(
         "raw",
         enc.encode(masterPassword),
         { name: "PBKDF2" },
         false,
         ["deriveKey"]
      );

      // 4. Sinh Key AES từ Password + Salt (Khớp cấu hình Java)
      const key = await window.crypto.subtle.deriveKey(
         {
            name: "PBKDF2",
            salt: saltBytes,
            iterations: 600000, // KHỚP VỚI JAVA
            hash: "SHA-256",
         },
         keyMaterial,
         { name: "AES-GCM", length: 256 },
         false,
         ["decrypt"]
      );

      // 5. Giải mã (AES-GCM)
      const decryptedBuffer = await window.crypto.subtle.decrypt(
         {
            name: "AES-GCM",
            iv: iv,
            tagLength: 128 // KHỚP VỚI JAVA
         },
         key,
         cipherText
      );

      // 6. Chuyển byte thành String
      return new TextDecoder().decode(decryptedBuffer);

   } catch (e) {
      console.error("Decrypt Error:", e);
      return null; // Trả về null nếu sai pass hoặc lỗi
   }
};