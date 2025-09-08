import api from './api';

// --- THIS IS NO LONGER MOCKED ---
export const getPendingVerifications = async () => {
  const response = await api.get('/college-admin/verifications/pending');
  return response.data;
};

export const approveRecord = async (recordId) => { /* ... */ };