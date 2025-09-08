import React from 'react';
import { Routes, Route } from 'react-router-dom';
import LandingPage from '../pages/LandingPage';
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';
import DashboardPage from '../pages/DashboardPage';
import ProtectedRoute from './ProtectedRoute';
import ProfilePage from '../pages/ProfilePage';
import EditProfilePage from '../pages/EditProfilePage';
import NetworkPage from '../pages/NetworkPage';
import JobsPage from '../pages/JobsPage';
import EventsPage from '../pages/EventsPage';
import DonationsPage from '../pages/DonationsPage';
import AdminDashboardPage from '../pages/AdminDashboardPage';
import NotFoundPage from '../pages/NotFoundPage';

const AppRoutes = () => {
  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />

      {/* Protected Routes */}
      <Route path="/dashboard" element={<ProtectedRoute><DashboardPage /></ProtectedRoute>} />
      <Route path="/profile/me" element={<ProtectedRoute><ProfilePage /></ProtectedRoute>} />
      <Route path="/profile/me/edit" element={<ProtectedRoute><EditProfilePage /></ProtectedRoute>} />
      <Route path="/network" element={<ProtectedRoute><NetworkPage /></ProtectedRoute>} />
      <Route path="/jobs" element={<ProtectedRoute><JobsPage /></ProtectedRoute>} />
      <Route path="/events" element={<ProtectedRoute><EventsPage /></ProtectedRoute>} />
      <Route path="/give-back" element={<ProtectedRoute><DonationsPage /></ProtectedRoute>} />
      <Route path="/admin/dashboard" element={<ProtectedRoute><AdminDashboardPage /></ProtectedRoute>} />

      {/* Catch-all 404 Route */}
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
};

export default AppRoutes;