import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { register } from '../apis/authApi';

function RegisterPage() {
   const navigate = useNavigate();
   const [formData, setFormData] = useState({
      username: '',
      email: '',
      phone: '',
      password: '',
      masterPassword: ''
   });
   const [errors, setErrors] = useState({});
   const [apiError, setApiError] = useState('');

   const handleChange = (e) => {
      const { name, value } = e.target;
      setFormData(prev => ({
         ...prev,
         [name]: value
      }));
      // Clear error when user types
      if (errors[name]) {
         setErrors(prev => ({ ...prev, [name]: '' }));
      }
      setApiError('');
   };

   const validate = () => {
      const newErrors = {};
      const usernameRegex = /^[a-zA-Z0-9]{6,20}$/;
      // Note: The backend regex for password might be strict. adjusted slightly for JS compatibility if needed but using the provided pattern logic.
      const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$/;
      const phoneRegex = /^[0-9]{10}$/;

      if (!usernameRegex.test(formData.username)) {
         newErrors.username = "Username must be 6-20 alphanumeric characters.";
      }
      if (!passwordRegex.test(formData.password)) {
         newErrors.password = "Password must be at least 8 chars, include uppercase, lowercase, number, and special char.";
      }
      if (formData.password !== formData.masterPassword) {
         // Ideally master password should be checked against its own rules, but often people want them same or confirmed. 
         // The prompt says "master password ... same format as password". 
         // It doesn't explicitly say it must be different or same, but usually "confirm password" is for same. 
         // The prompt description says "Creates a new user... and master password".
         // Let's assume they are separate fields as present in the UI. validiting format for both.
      }
      if (!passwordRegex.test(formData.masterPassword)) {
         newErrors.masterPassword = "Master Password must match the password requirements.";
      }

      if (!phoneRegex.test(formData.phone)) {
         newErrors.phone = "Phone number must be 10 digits.";
      }
      return newErrors;
   };

   const handleSubmit = async (e) => {
      e.preventDefault();
      const validationErrors = validate();
      if (Object.keys(validationErrors).length > 0) {
         setErrors(validationErrors);
         return;
      }

      try {
         await register(formData);
         navigate('/login');
      } catch (error) {
         console.error("Registration error:", error);
         if (error.response && error.response.status === 409) {
            setApiError("Email already exists.");
         } else if (error.response && error.response.data) {
            // Try to show backend validation error if available
            setApiError(JSON.stringify(error.response.data) || "Registration failed. Please try again.");
         } else {
            setApiError("Registration failed. Please check your network.");
         }
      }
   };

   return (
      <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-gray-50 to-white">
         <div className="w-full max-w-md bg-white rounded-xl shadow-lg p-8">
            {/* Header */}
            <div className="text-center mb-8">
               <div className="text-5xl mb-2">🔐</div>
               <h1 className="text-3xl font-bold text-gray-800 mb-1">Đăng ký</h1>
               <p className="text-gray-500 text-sm">Tạo tài khoản Cloud 2FA</p>
            </div>

            {apiError && (
               <div className="mb-4 p-3 bg-red-50 text-red-600 rounded-lg text-sm text-center">
                  {apiError}
               </div>
            )}

            {/* Form */}
            <form className="space-y-4" onSubmit={handleSubmit}>
               <div>
                  <label htmlFor="username" className="form-label">Tên đăng nhập</label>
                  <input
                     type="text"
                     id="username"
                     name="username"
                     className={`form-input ${errors.username ? 'border-red-500' : ''}`}
                     placeholder="Nhập tên đăng nhập"
                     value={formData.username}
                     onChange={handleChange}
                     required
                  />
                  {errors.username && <p className="text-red-500 text-xs mt-1">{errors.username}</p>}
               </div>

               <div>
                  <label htmlFor="email" className="form-label">Email</label>
                  <input
                     type="email"
                     id="email"
                     name="email"
                     className="form-input"
                     placeholder="Nhập địa chỉ email"
                     value={formData.email}
                     onChange={handleChange}
                     required
                  />
               </div>

               <div>
                  <label htmlFor="phone" className="form-label">Số điện thoại</label>
                  <input
                     type="tel"
                     id="phone"
                     name="phone"
                     className={`form-input ${errors.phone ? 'border-red-500' : ''}`}
                     placeholder="Nhập số điện thoại"
                     value={formData.phone}
                     onChange={handleChange}
                     required
                  />
                  {errors.phone && <p className="text-red-500 text-xs mt-1">{errors.phone}</p>}
               </div>

               <div>
                  <label htmlFor="password" className="form-label">Mật khẩu</label>
                  <input
                     type="password"
                     id="password"
                     name="password"
                     className={`form-input ${errors.password ? 'border-red-500' : ''}`}
                     placeholder="Nhập mật khẩu"
                     value={formData.password}
                     onChange={handleChange}
                     required
                  />
                  {errors.password && <p className="text-red-500 text-xs mt-1">{errors.password}</p>}
               </div>

               <div>
                  <label htmlFor="masterPassword" className="form-label">Master Password</label>
                  <input
                     type="password"
                     id="masterPassword"
                     name="masterPassword"
                     className={`form-input ${errors.masterPassword ? 'border-red-500' : ''}`}
                     placeholder="Nhập master password"
                     value={formData.masterPassword}
                     onChange={handleChange}
                     required
                  />
                  {errors.masterPassword && <p className="text-red-500 text-xs mt-1">{errors.masterPassword}</p>}
               </div>

               <button type="submit" className="btn-primary w-full mt-2">
                  Đăng ký
               </button>
            </form>

            {/* Footer */}
            <div className="text-center mt-6 pt-6 border-t border-gray-100">
               <p className="text-gray-500 text-sm">
                  Đã có tài khoản? <Link to="/login" className="text-blue-600 hover:underline">Đăng nhập</Link>
               </p>
            </div>
         </div>
      </div>
   );
}

export default RegisterPage;
