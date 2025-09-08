import React from 'react';
import Layout from '../components/layout/Layout';
import useAuth from '../hooks/useAuth';
import Button from '../components/ui/Button';

const DashboardPage = () => {
  const { user, logout } = useAuth();
  
  return (
    <Layout>
      <h1 className="text-3xl font-bold">Welcome, {user?.firstName}!</h1>
      <p className="mt-4">This is your dashboard. You have successfully logged in and this route is protected.</p>
      <div className="mt-8">
        <Button onClick={logout} className="bg-red-500 hover:bg-red-600">
          Logout
        </Button>
      </div>
    </Layout>
  );
};

export default DashboardPage;