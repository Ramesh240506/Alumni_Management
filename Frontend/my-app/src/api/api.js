import axios from 'axios';
import { getToken } from '../utils/tokenUtils';

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
});

// --- THIS IS THE NEW, CRITICAL PART ---
// Axios Request Interceptor
// This function will be called before every request is sent.
api.interceptors.request.use(
  (config) => {
    // Get the token from localStorage
    const token = getToken();
    // If the token exists, add it to the Authorization header
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // Handle request errors
    return Promise.reject(error);
  }
);
// --- END OF NEW PART ---

export default api;