import React from 'react';
import Layout from '../components/layout/Layout';
import Card from '../components/ui/Card';
import RegisterForm from '../components/auth/RegisterForm';

const RegisterPage = () => {
  return (
    <Layout>
      <div className="max-w-lg mx-auto">
        <Card>
          <h2 className="text-3xl font-bold text-center text-gray-800 mb-8">Create your Alumni Profile</h2>
          <RegisterForm />
        </Card>
      </div>
    </Layout>
  );
};

export default RegisterPage;