import api from './api';

export const getMyProfile = async () => {
  const response = await api.get('/alumni/me');
  return response.data;
};

export const updateMyProfile = async (profileData) => {
  const response = await api.put('/alumni/me', profileData);
  return response.data;
};

// You will need to build this endpoint on your backend.
// It should return a list of alumni from the same college as the current user.
export const getNetworkAlumni = async () => {
  // const response = await api.get('/alumni/network');
  // return response.data;

  // Returning MOCKED data until backend is ready
  console.warn("API call to getNetworkAlumni is mocked.");
  return [
    { alumniUserId: '1', firstName: 'Rahul', lastName: 'Sharma', currentCity: 'Mumbai', lat: 19.0760, lng: 72.8777 },
    { alumniUserId: '2', firstName: 'Priya', lastName: 'Singh', currentCity: 'Delhi', lat: 28.6139, lng: 77.2090 },
  ];
};