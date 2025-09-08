
import api from './api';

// You will need to build these endpoints on your backend.
export const getPendingVerifications = async () => {
  // const response = await api.get('/college-admin/verifications/pending');
  // return response.data;
  console.warn("API call to getPendingVerifications is mocked.");
  return [
    { recordId: 'record1', degreeName: 'Bachelor of Science', alumnus: { name: 'Test User 1' } },
    { recordId: 'record2', degreeName: 'Master of Arts', alumnus: { name: 'Test User 2' } }
  ];
};

export const approveRecord = async (recordId) => {
  const response = await api.post(`/college-admin/verifications/${recordId}/approve`);
  return response.data;
};