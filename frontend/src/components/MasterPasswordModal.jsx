import { useState } from 'react';

function MasterPasswordModal({ isOpen, onSubmit, onClose }) {
   const [password, setPassword] = useState('');
   const [error, setError] = useState('');
   const [isLoading, setIsLoading] = useState(false);

   const handleSubmit = async (e) => {
      e.preventDefault();
      if (!password) {
         setError('Vui lòng nhập Master Password');
         return;
      }
      setError('');
      setIsLoading(true);
      try {
         await onSubmit(password);
         setPassword(''); // Clear password after successful submission
      } catch (err) {
         // Error handling is done by parent or we can display specific error here
         // For now, let parent handle specific API errors, but we can catch basic issues
         setError(err.message || 'Có lỗi xảy ra');
      } finally {
         setIsLoading(false);
      }
   };

   if (!isOpen) return null;

   return (
      <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
         <div className="bg-white rounded-xl shadow-xl w-full max-w-sm p-6">
            <div className="text-center mb-6">
               <div className="text-4xl mb-3">🔐</div>
               <h3 className="text-lg font-bold text-gray-800 mb-2">Nhập Master Password</h3>
               <p className="text-gray-500 text-sm">
                  Để xem mã TOTP, vui lòng nhập Master Password của bạn.
               </p>
            </div>

            {error && (
               <div className="mb-4 p-2 bg-red-50 text-red-600 rounded text-sm text-center">
                  {error}
               </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-4">
               <div>
                  <input
                     type="password"
                     className="form-input text-center tracking-widest"
                     placeholder="Master Password"
                     value={password}
                     onChange={(e) => setPassword(e.target.value)}
                     autoFocus
                  />
               </div>

               <div className="flex gap-3">
                  {/* Optional: Cancel button if user wants to just stay on page without data */}
                  {/* <button type="button" className="btn-secondary flex-1" onClick={onClose}>Hủy</button> */}
                  <button
                     type="submit"
                     className="btn-primary w-full"
                     disabled={isLoading}
                  >
                     {isLoading ? 'Đang xác thực...' : 'Xác nhận'}
                  </button>
               </div>
            </form>
         </div>
      </div>
   );
}

export default MasterPasswordModal;
