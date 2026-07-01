import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { login } from '../apis/authApi';

function LoginPage() {
   const navigate = useNavigate();
   const [formData, setFormData] = useState({
      username: '',
      password: ''
   });

   const handleChange = (e) => {
      const { name, value } = e.target;
      setFormData(prev => ({
         ...prev,
         [name]: value
      }));
   };

   const [error, setError] = useState('');

   const handleSubmit = async (e) => {
      e.preventDefault();
      setError('');
      try {
         const result = await login(formData);
         // API returns structure { data: { accessToken, ... }, status: "SUCCESS" }
         if (result.status === 'SUCCESS' && result.data) {
            const { accessToken, refreshToken, user } = result.data;
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
            localStorage.setItem('user', JSON.stringify(user));
            navigate('/home');
         } else {
            setError('Đăng nhập không thành công: Phản hồi không hợp lệ');
         }
      } catch (err) {
         console.error('Login failed:', err);
         if (err.response && err.response.status === 401) {
            setError('Tên đăng nhập hoặc mật khẩu không đúng');
         } else {
            setError('Đăng nhập thất bại. Vui lòng thử lại sau.');
         }
      }
   };

   return (
      <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-gray-50 to-white">
         <div className="w-full max-w-md bg-white rounded-xl shadow-lg p-8">
            {/* Header */}
            <div className="text-center mb-8">
               <div className="text-5xl mb-2">🔐</div>
               <h1 className="text-3xl font-bold text-gray-800 mb-1">Cloud 2FA</h1>
               <p className="text-gray-500 text-sm">Đăng nhập để tiếp tục</p>
            </div>

            {error && (
               <div className="mb-4 p-3 bg-red-50 text-red-600 rounded-lg text-sm text-center">
                  {error}
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
                     className="form-input"
                     placeholder="Nhập tên đăng nhập"
                     value={formData.username}
                     onChange={handleChange}
                     required
                  />
               </div>

               <div>
                  <label htmlFor="password" className="form-label">Mật khẩu</label>
                  <input
                     type="password"
                     id="password"
                     name="password"
                     className="form-input"
                     placeholder="Nhập mật khẩu"
                     value={formData.password}
                     onChange={handleChange}
                     required
                  />
               </div>

               <button type="submit" className="btn-primary w-full mt-2">
                  Đăng nhập
               </button>
            </form>

            {/* Footer */}
            <div className="text-center mt-6 pt-6 border-t border-gray-100">
               <p className="text-gray-500 text-sm">
                  Chưa có tài khoản? <Link to="/register" className="text-blue-600 hover:underline">Đăng ký ngay</Link>
               </p>
            </div>
         </div>
      </div>
   );
}

export default LoginPage;
