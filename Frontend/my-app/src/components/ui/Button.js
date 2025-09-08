
import React from 'react';

const Button = ({ children, onClick, type = 'button', variant = 'primary', className = '' }) => {
  const baseStyles = 'font-bold py-2 px-4 rounded-lg focus:outline-none focus:shadow-outline transition duration-300';
  
  const variants = {
    primary: 'bg-indigo-600 text-white hover:bg-indigo-700',
    secondary: 'bg-gray-200 text-gray-800 hover:bg-gray-300',
  };

  return (
    <button
      type={type}
      onClick={onClick}
      className={`${baseStyles} ${variants[variant]} ${className}`}
    >
      {children}
    </button>
  );
};

export default Button;