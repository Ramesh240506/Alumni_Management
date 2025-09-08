
export const setToken = (token) => {
  localStorage.setItem('alumni_token', token);
};

export const getToken = () => {
  return localStorage.getItem('alumni_token');
};

export const removeToken = () => {
  localStorage.removeItem('alumni_token');
};