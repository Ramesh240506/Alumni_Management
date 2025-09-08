
import React from 'react';

const Footer = () => {
  return (
    <footer className="bg-gray-800 text-white mt-auto">
      <div className="container mx-auto px-6 py-4 text-center">
        <p>&copy; {new Date().getFullYear()} AlumniBridge. All Rights Reserved.</p>
        <p>A Hackathon Project by Team AlumniCoders</p>
      </div>
    </footer>
  );
};

export default Footer;