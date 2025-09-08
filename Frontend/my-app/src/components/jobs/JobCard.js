import React from 'react';
import Card from '../ui/Card';
import { formatDate } from '../../utils/formatDate';

const JobCard = ({ job }) => {
  return (
    <Card>
      <div className="flex flex-col h-full">
        <div className="flex-grow">
          <div className="flex justify-between items-start">
            <h2 className="text-xl font-bold text-gray-800">{job.title}</h2>
            {job.isReferral && (
              <span className="bg-blue-100 text-blue-800 text-xs font-semibold px-2.5 py-0.5 rounded-full">Referral</span>
            )}
          </div>
          <p className="text-md text-gray-600 font-semibold">{job.company.name}</p>
          <p className="text-sm text-gray-500 mt-1">{job.location}</p>
          <p className="text-sm text-gray-500">{job.type.replace('_', ' ')}</p>
          <p className="mt-4 text-gray-700 text-sm line-clamp-3">{job.description}</p>
        </div>
        <div className="mt-4 pt-4 border-t text-xs text-gray-500">
          <p>Posted by: {job.postedBy.name}</p>
          <p>On: {formatDate(job.createdAt)}</p>
        </div>
      </div>
    </Card>
  );
};

export default JobCard;