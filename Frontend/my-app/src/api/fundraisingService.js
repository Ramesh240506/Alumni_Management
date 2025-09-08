
import api from './api';

export const getCampaigns = async () => {
  const response = await api.get('/fundraising/campaigns');
  return response.data;
};

export const makeDonation = async (donationData) => {
  const response = await api.post('/donations', donationData);
  return response.data;
};