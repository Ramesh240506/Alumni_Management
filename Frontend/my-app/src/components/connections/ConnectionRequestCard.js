
import React from 'react';
import Card from '../ui/Card';
import Avatar from '../ui/Avatar';
import Button from '../ui/Button';

const ConnectionRequestCard = ({ request }) => {
  const handleResponse = (status) => {
    // Call connectionService.respondToConnectionRequest(request.user.userId, status)
    alert(`Request ${status.toLowerCase()}`);
  };

  return (
    <Card className="flex items-center justify-between">
      <div className="flex items-center space-x-4">
        <Avatar user={request.user} />
        <div>
          <p className="font-bold">{request.user.firstName} {request.user.lastName}</p>
          <p className="text-sm text-gray-500">Wants to connect with you.</p>
        </div>
      </div>
      <div className="flex space-x-2">
        <Button onClick={() => handleResponse('ACCEPTED')}>Accept</Button>
        <Button onClick={() => handleResponse('REJECTED')} variant="secondary">Decline</Button>
      </div>
    </Card>
  );
};

export default ConnectionRequestCard;