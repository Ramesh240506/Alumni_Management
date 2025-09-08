import api from './api';

export const getMyProfile = async () => { /* ... */ };
export const updateMyProfile = async (profileData) => { /* ... */ };

// --- THIS IS NO LONGER MOCKED ---
export const getNetworkAlumni = async () => {
  const response = await api.get('/alumni/network');
  return response.data;
};