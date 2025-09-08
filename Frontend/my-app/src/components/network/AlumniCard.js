
import React from 'react';
import Card from '../ui/Card';
import Avatar from '../ui/Avatar';
import ConnectButton from '../connections/ConnectButton';

const AlumniCard = ({ alumnus }) => {
  return (
    <Card className="text-center">
      <Avatar user={alumnus} />
      <h3 className="font-bold mt-2">{alumnus.firstName} {alumnus.lastName}</h3>
      <p className="text-sm text-gray-500">{alumnus.currentCity}</p>
      <div className="mt-4">
        <ConnectButton targetUserId={alumnus.alumniUserId} />
      </div>
    </Card>
  );
};

export default AlumniCard;