
import api from './api';

// Placeholder for fetching connection requests for the current user.
// You would need to add a GET endpoint to your ConnectionController for this.
export const getConnectionRequests = async () => {
  console.warn("API call to getConnectionRequests is mocked.");
  return [
    { user: { userId: '123', firstName: 'Priya', lastName: 'Singh' }, status: 'PENDING' }
  ];
};

export const respondToConnectionRequest = async (requesterId, status) => {
  const response = await api.put(`/connections/respond/${requesterId}`, { status });
  return response.data;
};