import React, { createContext, useState, useEffect } from 'react';
import { getToken, setToken, removeToken } from '../utils/tokenUtils';
import { getMyProfile } from '../api/alumniService';
import Spinner from '../components/ui/Spinner';

export const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [token, setAuthState] = useState(getToken());
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUser = async () => {
      if (token) {
        try {
          const profileData = await getMyProfile();
          setUser(profileData);
        } catch (error) {
          console.error("Failed to fetch user profile, logging out.", error);
          logout(); // If token is invalid, log the user out
        }
      }
      setLoading(false);
    };

    fetchUser();
  }, [token]);

  const login = (newToken) => {
    setToken(newToken);
    setAuthState(newToken);
  };

  const logout = () => {
    removeToken();
    setAuthState(null);
    setUser(null);
  };

  const isAuthenticated = !!token;

  // Show a loading spinner until we know if the user is authenticated or not
  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <Spinner />
      </div>
    );
  }

  const value = {
    token,
    user,
    isAuthenticated,
    loading,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};