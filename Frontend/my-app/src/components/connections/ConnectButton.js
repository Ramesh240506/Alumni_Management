
import React from 'react';
import Button from '../ui/Button';

const ConnectButton = ({ targetUserId }) => {
  const handleConnect = () => {
    // Call connectionService.sendConnectionRequest(targetUserId)
    alert(`Connection request sent to user ${targetUserId}`);
  };

  return <Button onClick={handleConnect}>Connect</Button>;
};

export default ConnectButton;