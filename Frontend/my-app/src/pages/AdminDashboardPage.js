import React from 'react';
import Layout from '../components/layout/Layout';
import useAuth from '../hooks/useAuth';
import VerificationQueue from '../components/admin/VerificationQueue';
import CreateEventForm from '../components/admin/CreateEventForm';
import CreateCampaignForm from '../components/admin/CreateCampaignForm';

const AdminDashboardPage = () => {
  const { user } = useAuth();

  const renderAdminContent = () => {
    if (!user || !user.role) return <p>Loading admin data...</p>;

    switch (user.role) {
      case 'COLLEGE_ADMIN':
        return (
          <div className="grid lg:grid-cols-2 gap-8">
            <VerificationQueue />
            <div className="space-y-8">
              <CreateEventForm />
              <CreateCampaignForm />
            </div>
          </div>
        );
      case 'EMPLOYER_REP':
        return <p>Employer Dashboard: Pending work verifications for your company will be shown here.</p>;
      case 'ADMIN':
        return <p>Super Admin Dashboard: Tools to manage colleges and companies will be shown here.</p>;
      default:
        return <p>You do not have administrative access.</p>;
    }
  };

  return (
    <Layout>
      <h1 className="text-3xl font-bold mb-6">Admin Dashboard</h1>
      {renderAdminContent()}
    </Layout>
  );
};

export default AdminDashboardPage;