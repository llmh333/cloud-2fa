// hooks/useTotpList.js
import { useState, useEffect, useRef } from 'react';
import * as OTPAuth from "otpauth";
import { decryptSecret } from '../utils/crypto';

export const useTotpList = (apiData, masterPassword) => {
   const [decryptedAccounts, setDecryptedAccounts] = useState([]);
   const [loading, setLoading] = useState(true);

   // 1. Giai đoạn Giải mã (Chạy khi data hoặc password thay đổi)
   useEffect(() => {
      const initAccounts = async () => {
         if (!apiData || !masterPassword) return;
         // Ensure apiData is an array to avoid runtime errors
         if (!Array.isArray(apiData)) return;
         setLoading(true);

         const results = await Promise.all(
            apiData.map(async (item) => {
               // Gọi hàm giải mã ta vừa viết
               const rawSecret = await decryptSecret(
                  item.secretBlob,
                  masterPassword,
                  item.encryptionSalt
               );

               if (!rawSecret) {
                  return { ...item, error: true, code: "LOCKED" };
               }

               // Normalize algorithm names from backend (e.g. HmacSHA1 -> SHA1)
               const normalizeAlgorithm = (alg) => {
                  if (!alg) return 'SHA1';
                  const a = String(alg).toUpperCase();
                  if (a.includes('SHA1')) return 'SHA1';
                  if (a.includes('SHA256')) return 'SHA256';
                  if (a.includes('SHA512')) return 'SHA512';
                  // fallback to SHA1
                  return 'SHA1';
               };

               let totpInstance = null;
               try {
                  totpInstance = new OTPAuth.TOTP({
                     issuer: item.issuer,
                     label: item.accountName,
                     algorithm: normalizeAlgorithm(item.algorithm),
                     digits: item.digits || 6,
                     period: item.period || 30,
                     secret: rawSecret, // Secret đã giải mã
                  });
               } catch (e) {
                  // If OTPAuth throws (unknown algorithm, bad secret), mark this account as errored
                  console.error('Failed to create TOTP instance for', item.id, e);
                  return { ...item, error: true, code: 'ERROR', errorMsg: String(e) };
               }

               return {
                  ...item,
                  totpInstance, // Lưu instance để không phải new lại
                  rawSecret,    // Lưu secret để hiển thị nếu cần (view QR)
                  code: "--- ---",
                  progress: 0,
                  remaining: 0,
                  error: false
               };
            })
         );

         setDecryptedAccounts(results);
         setLoading(false);
      };

      initAccounts();
   }, [apiData, masterPassword]);

   // 2. Giai đoạn Tick (Chạy mỗi giây để update mã)
   useEffect(() => {
      if (loading || decryptedAccounts.length === 0) return;

      const tick = () => {
         setDecryptedAccounts(currentAccounts =>
            currentAccounts.map(acc => {
               if (acc.error || !acc.totpInstance) return acc;

               // Tính toán mã mới
               const code = acc.totpInstance.generate();

               // Tính thời gian đếm ngược
               const period = acc.period || 30;
               const epoch = Math.floor(Date.now() / 1000);
               const remaining = period - (epoch % period);
               const progress = remaining / period; // 1.0 -> 0.0

               // Format mã cho đẹp (123456 -> 123 456)
               const formattedCode = code.match(/.{1,3}/g).join(" ");

               return { ...acc, code: formattedCode, remaining, progress };
            })
         );
      };

      // Chạy ngay lập tức 1 lần
      tick();

      // Set interval chạy mỗi giây
      const interval = setInterval(tick, 1000);
      return () => clearInterval(interval);
   }, [loading]); // Chỉ chạy lại khi loading xong

   return { accounts: decryptedAccounts, loading };
};