
import React from 'react';
import VerifiedBadge from '../ui/VerifiedBadge';

const AcademicRecordCard = ({ record }) => {
  return (
    <div className="bg-white p-4 rounded-lg shadow-md">
      <div className="flex justify-between items-start">
        <div>
          <h3 className="font-bold text-lg">{record.degreeName}</h3>
          <p className="text-gray-600">{record.major}</p>
          <p className="text-sm text-gray-500">Graduated: {record.graduationYear}</p>
        </div>
        {record.status === 'VERIFIED' && <VerifiedBadge />}
      </div>
    </div>
  );
};

export default AcademicRecordCard;