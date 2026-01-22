import { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import TotpRow from '../components/TotpRow';
import AddSecretModal from '../components/AddSecretModal';
import LogoutConfirmModal from '../components/LogoutConfirmModal';
import MasterPasswordModal from '../components/MasterPasswordModal';
import { syncData } from '../apis/totpApi';
import { useTotpList } from '../hooks/useTotpList';

function HomePage() {
   const navigate = useNavigate();
   const [apiData, setApiData] = useState([]);
   const [syncError, setSyncError] = useState('');
   const [accounts, setAccounts] = useState([]);
   const [isModalOpen, setIsModalOpen] = useState(false);
   const CACHE_KEY = 'totpSyncData_v1';
   const saveCache = (data) => {
      try {
         localStorage.setItem(CACHE_KEY, JSON.stringify(data));
      } catch (e) {
         console.warn('Failed to save sync cache', e);
      }
   };

   const loadCache = () => {
      try {
         const raw = localStorage.getItem(CACHE_KEY);
         if (!raw) return null;
         return JSON.parse(raw);
      } catch (e) {
         console.warn('Failed to load sync cache', e);
         return null;
      }
   };
   const [isLogoutConfirmOpen, setIsLogoutConfirmOpen] = useState(false);
   const [isMasterPasswordModalOpen, setIsMasterPasswordModalOpen] = useState(true);
   const [masterPassword, setMasterPassword] = useState('');

   const isLoadingFromSync = false;

   const handleMasterPasswordSubmit = async (pwd) => {
      try {
         setSyncError('');
         const data = await syncData(pwd);
         // data expected: array of items with secretBlob, encryptionSalt, issuer, accountName, etc.
         const list = Array.isArray(data) ? data : [];
         setApiData(list);
         saveCache(list);
         setMasterPassword(pwd);
         setIsMasterPasswordModalOpen(false);
      } catch (err) {
         console.error('Sync failed', err);
         // Fallback to cached data if available so offline generation works
         const cached = loadCache();
         if (cached && Array.isArray(cached) && cached.length > 0) {
            setApiData(cached);
            setMasterPassword(pwd);
            setIsMasterPasswordModalOpen(false);
            setSyncError('Đang sử dụng dữ liệu đã đồng bộ trước đó (offline).');
            return;
         }

         // Provide friendly message for common status codes
         if (err.response && err.response.status === 400) {
            setSyncError('Mật khẩu chính không đúng. Vui lòng thử lại.');
         } else if (err.response && err.response.status === 401) {
            setSyncError('Không được phép. Vui lòng đăng nhập lại.');
         } else if (err.response && err.response.status === 404) {
            setSyncError('API không tìm thấy. Kiểm tra cấu hình backend.');
         } else {
            setSyncError('Đồng bộ thất bại. Vui lòng thử lại sau.');
         }
         setApiData([]);
      }
   };

   // Use hook to decrypt secrets and generate codes client-side
   const { accounts: decryptedAccounts, loading } = useTotpList(apiData, masterPassword);

   useEffect(() => {
      setAccounts(decryptedAccounts);
   }, [decryptedAccounts]);

   const onPasswordSubmit = handleMasterPasswordSubmit;

   // ... existing handlers ...

   const handleAddAccount = (newAccount) => {
      // ... existing logic needs update or removal ...
      // Since the backend handles accounts, "Add Secret" should technically call an API to add account
      // then refresh access.
      // The prompt didn't ask to implement "Add" via API yet, just "Login -> Get TOTP".
      // Existing Add logic was local. I should probably disable or update it?
      // Leaving it as "mock" or just appending to local list?
      // The API `generate` fetches "stored secrets". 
      // So adding locally won't persist if we don't send to backend.
      // But user task is specifically about "generate totp api".
      // I'll leave Add as local-only or mock for now to not break the UI, 
      // but note that it won't persist. 
      // Or better: The prompt implies a read-only view of what's on server.
      // I will keep the Add button working locally but warn or effectively it just adds to the UI list temporarily.

      const account = {
         ...newAccount,
         id: Date.now().toString(),
         code: '000000', // Mock
         name: newAccount.name
      };
      setAccounts(prev => [...prev, account]);
   };

   const handleLogoutClick = () => {
      setIsLogoutConfirmOpen(true);
   };

   const handleConfirmLogout = () => {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      localStorage.removeItem('user');
      // Clear cached sync data on logout for safety
      try { localStorage.removeItem(CACHE_KEY); } catch (e) { }
      navigate('/login');
   };

   // On mount, load cached sync data (if any) so user can use offline after entering master password
   useEffect(() => {
      const cached = loadCache();
      if (cached && Array.isArray(cached) && cached.length > 0) {
         setApiData(cached);
      }
   }, []);

   return (
      <div className="min-h-screen bg-gray-50">
         {/* Header */}
         <header className="bg-white px-6 py-4 shadow-sm flex items-center justify-between sticky top-0 z-50">
            <div className="flex items-center gap-2">
               <span className="text-2xl">🔐</span>
               <span className="text-xl font-bold text-gray-800">Cloud 2FA</span>
            </div>
            <div className="flex items-center gap-4">
               <button className="btn-outline py-2 px-4 text-sm" onClick={handleLogoutClick}>
                  Đăng xuất
               </button>
            </div>
         </header>

         {/* Content */}
         <main className="max-w-3xl mx-auto p-6">
            {/* Toolbar */}
            <div className="flex items-center justify-between mb-6">
               <h1 className="text-lg font-semibold text-gray-800">
                  Tài khoản của bạn
                  <span className="text-gray-500 font-normal ml-2">({accounts.length})</span>
               </h1>
               <button
                  className="btn-primary flex items-center gap-2"
                  onClick={() => setIsModalOpen(true)}
               >
                  ➕ Thêm Secret
               </button>
            </div>

            {/* TOTP List */}
            {syncError && (
               <div className="mb-4 p-3 bg-red-50 text-red-600 rounded-lg text-sm text-center">
                  {syncError}
               </div>
            )}
            <div className="flex flex-col">
               {loading ? (
                  <div className="text-center py-12 px-6 bg-white rounded-xl shadow-sm">Đang đồng bộ và giải mã...</div>
               ) : accounts.length > 0 ? (
                  accounts.map(account => (
                     <TotpRow
                        key={account.id}
                        account={account}
                        remaining={account.remaining}
                     />
                  ))
               ) : (
                  <div className="text-center py-12 px-6 bg-white rounded-xl shadow-sm">
                     <div className="text-6xl mb-4">🔑</div>
                     <h2 className="text-lg font-semibold text-gray-800 mb-2">Chưa có tài khoản nào</h2>
                     <p className="text-gray-500 mb-6">
                        Thêm secret đầu tiên để bắt đầu sử dụng 2FA
                     </p>
                     <button
                        className="btn-primary"
                        onClick={() => setIsModalOpen(true)}
                     >
                        ➕ Thêm Secret
                     </button>
                  </div>
               )}
            </div>
         </main>

         {/* Modal */}
         <AddSecretModal
            isOpen={isModalOpen}
            onClose={() => setIsModalOpen(false)}
            onAdd={handleAddAccount}
         />

         {/* Logout Confirm Modal */}
         <LogoutConfirmModal
            isOpen={isLogoutConfirmOpen}
            onClose={() => setIsLogoutConfirmOpen(false)}
            onConfirm={handleConfirmLogout}
         />

         {/* Master Password Modal */}
         <MasterPasswordModal
            isOpen={isMasterPasswordModalOpen}
            onSubmit={onPasswordSubmit}
            onClose={() => { }} // Cannot close without auth
         />
      </div>
   );
}

export default HomePage;
