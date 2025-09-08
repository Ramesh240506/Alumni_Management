
import React from 'react';
import Button from '../ui/Button';

const RsvpButton = ({ eventId, onRsvp }) => {
  return (
    <div className="mt-6 flex space-x-2">
      <Button onClick={() => onRsvp(eventId, 'ATTENDING')}>Attend</Button>
      <Button onClick={() => onRsvp(eventId, 'MAYBE')} variant="secondary">Maybe</Button>
    </div>
  );
};

export default RsvpButton;