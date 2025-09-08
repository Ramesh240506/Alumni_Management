
import React from 'react';
import Layout from '../components/layout/Layout';
import { Link } from 'react-router-dom';
import Button from '../components/ui/Button';

const LandingPage = () => {
  return (
    <Layout>
      <div className="text-center">
        <h1 className="text-4xl md:text-6xl font-extrabold text-gray-800">
          Welcome to <span className="text-indigo-600">AlumniBridge</span>
        </h1>
        <p className="mt-4 text-lg text-gray-600">
          Reconnect with your peers, mentor the next generation, and stay engaged with your alma mater.
        </p>
        <div className="mt-8">
          <Link to="/register">
            <Button variant="primary" className="text-lg py-3 px-8">
              Join Now
            </Button>
          </Link>
        </div>
      </div>
      
      {/* Feature highlight section */}
      <div className="mt-20 grid md:grid-cols-3 gap-10">
        <div className="text-center p-6 bg-white rounded-lg shadow-md">
          <h3 className="text-xl font-bold mb-2">Verified Credentials</h3>
          <p>Your academic and professional achievements, verified and tamper-proof.</p>
        </div>
        <div className="text-center p-6 bg-white rounded-lg shadow-md">
          <h3 className="text-xl font-bold mb-2">AI Career Bridge</h3>
          <p>Get personalized recommendations for mentors, jobs, and career paths.</p>
        </div>
        <div className="text-center p-6 bg-white rounded-lg shadow-md">
          <h3 className="text-xl font-bold mb-2">Global Network</h3>
          <p>Visualize and connect with alumni from around the world on our interactive map.</p>
        </div>
      </div>
    </Layout>
  );
};

export default LandingPage;