import api from './axiosConfig';

export const generateTotps = async (masterPassword) => {
   try {
      const response = await api.post('/api/v1/totps/generate', { masterPassword });
      return response.data;
   } catch (error) {
      throw error;
   }
};

// Sync: retrieve encrypted secrets and related metadata for offline generation
export const syncData = async (masterPassword) => {
   try {
      const response = await api.post('/api/v1/totps/sync/data', { masterPassword });
      // Backend may wrap payload as { data: [...], status, ... }
      if (response && response.data && Array.isArray(response.data.data)) {
         return response.data.data;
      }
      // Fallback: if API returns array directly or other shape, return what we have
      return response.data;
   } catch (error) {
      throw error;
   }
};
