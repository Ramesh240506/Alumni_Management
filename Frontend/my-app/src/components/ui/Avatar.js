
import React from 'react';

const Avatar = ({ user }) => {
  const getInitials = (firstName, lastName) => {
    return `${firstName?.charAt(0) || ''}${lastName?.charAt(0) || ''}`.toUpperCase();
  };

  return (
    <div className="w-10 h-10 rounded-full bg-indigo-600 text-white flex items-center justify-center font-bold text-lg">
      {/* Logic to show picture if available, otherwise initials */}
      {user ? getInitials(user.firstName, user.lastName) : '?'}
    </div>
  );
};

export default Avatar;