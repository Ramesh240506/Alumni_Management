import React from 'react';
import Layout from '../components/layout/Layout';
import Card from '../components/ui/Card';
import EditProfileForm from '../components/profile/EditProfileForm';

const EditProfilePage = () => {
  return (
    <Layout>
      <div className="max-w-2xl mx-auto">
        <Card>
          <h1 className="text-3xl font-bold mb-6">Edit Your Profile</h1>
          <EditProfileForm />
        </Card>
      </div>
    </Layout>
  );
};

export default EditProfilePage;