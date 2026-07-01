import api from './axiosConfig';

export const register = async (userData) => {
   try {
      const response = await api.post('/api/v1/auth/register', userData);
      return response.data;
   } catch (error) {
      throw error;
   }
};

export const login = async (credentials) => {
   try {
      const response = await api.post('/api/v1/auth/login', credentials);
      return response.data;
   } catch (error) {
      throw error;
   }
};
