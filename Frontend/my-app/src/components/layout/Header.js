import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../ui/Button';
import useAuth from '../../hooks/useAuth';
import Avatar from '../ui/Avatar';

const Header = () => {
  const { isAuthenticated, user, logout } = useAuth();

  return (
    <header className="bg-white shadow-md sticky top-0 z-50">
      <nav className="container mx-auto px-6 py-3 flex justify-between items-center">
        <Link to="/" className="text-2xl font-bold text-indigo-600">
          Alumni<span className="text-gray-700">Bridge</span>
        </Link>
        <div className="flex items-center space-x-4">
          {isAuthenticated ? (
            <>
              <Link to="/jobs" className="text-gray-600 hover:text-indigo-600">Jobs</Link>
              <Link to="/events" className="text-gray-600 hover:text-indigo-600">Events</Link>
              <Link to="/network" className="text-gray-600 hover:text-indigo-600">Network</Link>
              <Button onClick={logout} variant="secondary">Logout</Button>
              <Link to="/profile/me">
                <Avatar user={user} />
              </Link>
            </>
          ) : (
            <>
              <Link to="/login" className="text-gray-600 hover:text-indigo-600">
                Login
              </Link>
              <Link to="/register">
                <Button variant="primary">Register</Button>
              </Link>
            </>
          )}
        </div>
      </nav>
    </header>
  );
};

export default Header;