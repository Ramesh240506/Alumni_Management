
import React from 'react';

const JobFilters = () => {
  return (
    <div className="bg-white p-4 rounded-lg shadow-md mb-6">
      <div className="grid md:grid-cols-3 gap-4">
        <input placeholder="Search by title or company..." className="shadow-sm border rounded-lg w-full py-2 px-3" />
        <select className="shadow-sm border rounded-lg w-full py-2 px-3">
          <option value="">All Job Types</option>
          <option value="FULL_TIME">Full Time</option>
          <option value="INTERNSHIP">Internship</option>
        </select>
        <input placeholder="Location..." className="shadow-sm border rounded-lg w-full py-2 px-3" />
      </div>
    </div>
  );
};

export default JobFilters;