
import React from 'react';

const Input = ({ id, name, type = 'text', value, onChange, placeholder, className = '' }) => {
  return (
    <input
      id={id}
      name={name}
      type={type}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      className={`shadow appearance-none border rounded-lg w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-indigo-400 ${className}`}
    />
  );
};

export default Input;