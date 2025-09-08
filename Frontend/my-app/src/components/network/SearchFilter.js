
import React from 'react';

const SearchFilter = () => {
  return (
    <div className="bg-white p-4 rounded-lg shadow-md mb-6">
      <input 
        placeholder="Search alumni by name, city, or company..." 
        className="shadow-sm border rounded-lg w-full py-2 px-3" 
      />
    </div>
  );
};

export default SearchFilter;