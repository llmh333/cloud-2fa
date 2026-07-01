import React from 'react';

function LogoutConfirmModal({ isOpen, onClose, onConfirm }) {
   if (!isOpen) return null;

   return (
      <div
         className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4"
         onClick={onClose}
      >
         <div
            className="bg-white rounded-xl shadow-xl w-full max-w-sm p-6 transform transition-all"
            onClick={e => e.stopPropagation()}
         >
            <div className="text-center mb-6">
               <div className="text-4xl mb-3">🚪</div>
               <h3 className="text-lg font-bold text-gray-800 mb-2">Đăng xuất</h3>
               <p className="text-gray-500 text-sm">
                  Bạn có chắc chắn muốn đăng xuất khỏi tài khoản không?
               </p>
            </div>

            <div className="flex gap-3">
               <button
                  className="bg-gray-100 text-gray-800 font-medium py-2.5 px-4 rounded-xl hover:bg-gray-200 transition-colors duration-200 flex items-center justify-center gap-2 flex-1"
                  onClick={onClose}
               >
                  Hủy
               </button>
               <button
                  className="bg-red-500 text-white font-medium py-2.5 px-4 rounded-xl hover:bg-red-600 transition-colors duration-200 flex items-center justify-center gap-2 flex-1 shadow-sm shadow-red-200"
                  onClick={onConfirm}
               >
                  Đăng xuất
               </button>
            </div>
         </div>
      </div>
   );
}

export default LogoutConfirmModal;
