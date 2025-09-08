import React from 'react';
import Layout from '../components/layout/Layout';
import Card from '../components/ui/Card';
import LoginForm from '../components/auth/LoginForm'; 

const LoginPage = () => {
  return (
    <Layout>
      <div className="max-w-md mx-auto">
        <Card>
          <h2 className="text-3xl font-bold text-center text-gray-800 mb-8">Login to your Account</h2>
          <LoginForm />
        </Card>
      </div>
    </Layout>
  );
};

export default LoginPage;