import api from './api';

// --- THIS IS NO LONGER MOCKED ---
export const getConnectionRequests = async () => {
  const response = await api.get('/connections/requests/pending');
  return response.data;
};

export const respondToConnectionRequest = async (requesterId, status) => { /* ... */ };