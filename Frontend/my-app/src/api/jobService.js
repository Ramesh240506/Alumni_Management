
import api from './api';

export const getOpenJobs = async () => {
  const response = await api.get('/jobs');
  return response.data;
};

export const createJobReferral = async (jobData) => {
  const response = await api.post('/jobs', jobData);
  return response.data;
};