import { useState } from 'react';

function AddSecretModal({ isOpen, onClose, onAdd }) {
   const [formData, setFormData] = useState({
      secret: '',
      issuer: '',
      name: '',
      digits: '',
      period: ''
   });

   const handleChange = (e) => {
      const { name, value } = e.target;
      setFormData(prev => ({
         ...prev,
         [name]: value
      }));
   };

   const handleSubmit = (e) => {
      e.preventDefault();

      const newAccount = {
         secret: formData.secret,
         issuer: formData.issuer,
         name: formData.name,
         digits: formData.digits ? parseInt(formData.digits) : 6,
         period: formData.period ? parseInt(formData.period) : 30
      };

      onAdd(newAccount);
      setFormData({
         secret: '',
         issuer: '',
         name: '',
         digits: '',
         period: ''
      });
      onClose();
   };

   const handleQrUpload = () => {
      // TODO: Implement QR code upload functionality
      alert('Chức năng quét QR code sẽ được phát triển thêm');
   };

   if (!isOpen) return null;

   return (
      <div
         className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4"
         onClick={onClose}
      >
         <div
            className="bg-white rounded-xl shadow-xl w-full max-w-lg max-h-[90vh] overflow-y-auto"
            onClick={e => e.stopPropagation()}
         >
            {/* Header */}
            <div className="flex items-center justify-between p-6 border-b border-gray-100">
               <h2 className="text-xl font-semibold text-gray-800">Thêm Secret Mới</h2>
               <button
                  className="p-2 bg-transparent border-none text-xl cursor-pointer text-gray-500 rounded-lg hover:bg-gray-100 hover:text-gray-800 transition-all duration-200"
                  onClick={onClose}
               >
                  ✕
               </button>
            </div>

            {/* Body */}
            <div className="p-6">
               <form className="space-y-4" onSubmit={handleSubmit}>
                  <div>
                     <label htmlFor="secret" className="form-label">Secret Key *</label>
                     <input
                        type="text"
                        id="secret"
                        name="secret"
                        className="form-input"
                        placeholder="Nhập secret key (Base32)"
                        value={formData.secret}
                        onChange={handleChange}
                        required
                     />
                  </div>

                  <div>
                     <label htmlFor="issuer" className="form-label">Issuer *</label>
                     <input
                        type="text"
                        id="issuer"
                        name="issuer"
                        className="form-input"
                        placeholder="VD: Google, GitHub, ..."
                        value={formData.issuer}
                        onChange={handleChange}
                        required
                     />
                  </div>

                  <div>
                     <label htmlFor="name" className="form-label">Tên tài khoản *</label>
                     <input
                        type="text"
                        id="name"
                        name="name"
                        className="form-input"
                        placeholder="VD: user@example.com"
                        value={formData.name}
                        onChange={handleChange}
                        required
                     />
                  </div>

                  <div className="grid grid-cols-2 gap-4">
                     <div>
                        <label htmlFor="digits" className="form-label">
                           Số chữ số <span className="text-gray-400 text-xs font-normal">(mặc định: 6)</span>
                        </label>
                        <input
                           type="number"
                           id="digits"
                           name="digits"
                           className="form-input"
                           placeholder="6"
                           min="4"
                           max="8"
                           value={formData.digits}
                           onChange={handleChange}
                        />
                     </div>

                     <div>
                        <label htmlFor="period" className="form-label">
                           Thời gian (giây) <span className="text-gray-400 text-xs font-normal">(mặc định: 30)</span>
                        </label>
                        <input
                           type="number"
                           id="period"
                           name="period"
                           className="form-input"
                           placeholder="30"
                           min="15"
                           max="60"
                           value={formData.period}
                           onChange={handleChange}
                        />
                     </div>
                  </div>

                  {/* QR Upload Section */}
                  <div className="mt-4 pt-4 border-t border-gray-100">
                     <button
                        type="button"
                        className="w-full p-6 border-2 border-dashed border-gray-200 bg-gray-50 rounded-lg text-gray-500 cursor-pointer flex flex-col items-center gap-2 hover:border-blue-600 hover:text-blue-600 hover:bg-gray-100 transition-all duration-200"
                        onClick={handleQrUpload}
                     >
                        <span className="text-3xl">📷</span>
                        <span>Quét QR Code</span>
                        <span className="text-gray-400 text-xs">(Chức năng phát triển thêm)</span>
                     </button>
                  </div>
               </form>
            </div>

            {/* Footer */}
            <div className="flex gap-4 p-6 border-t border-gray-100">
               <button type="button" className="btn-secondary flex-1" onClick={onClose}>
                  Hủy
               </button>
               <button type="submit" className="btn-primary flex-1" onClick={handleSubmit}>
                  Thêm Secret
               </button>
            </div>
         </div>
      </div>
   );
}

export default AddSecretModal;
