
import React from 'react';
import Button from '../ui/Button';
import { Link } from 'react-router-dom';

const ProfileHeader = ({ user }) => {
  if (!user) return null;

  return (
    <div className="bg-white p-6 rounded-lg shadow-md text-center">
      <div className="w-24 h-24 rounded-full bg-indigo-600 text-white flex items-center justify-center font-bold text-4xl mx-auto">
        {user.firstName?.charAt(0)}{user.lastName?.charAt(0)}
      </div>
      <h2 className="mt-4 text-3xl font-bold">{user.firstName} {user.lastName}</h2>
      <p className="text-gray-600">{user.email}</p>
      <Link to="/profile/me/edit" className="mt-4 inline-block">
        <Button>Edit Profile</Button>
      </Link>
    </div>
  );
};

export default ProfileHeader;