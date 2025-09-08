import React from 'react';
import VerifiedBadge from '../ui/VerifiedBadge';

const WorkExperienceCard = ({ experience }) => {
  return (
    <div className="bg-white p-4 rounded-lg shadow-md">
      <div className="flex justify-between items-start">
        <div>
          <h3 className="font-bold text-lg">{experience.jobTitle}</h3>
          <p className="text-gray-600">{experience.company?.name}</p>
          <p className="text-sm text-gray-500">{experience.startDate} - {experience.endDate || 'Present'}</p>
        </div>
        {experience.status === 'VERIFIED' && <VerifiedBadge />}
      </div>
    </div>
  );
};

export default WorkExperienceCard;