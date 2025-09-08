
import React from 'react';
import Card from '../ui/Card';
import Button from '../ui/Button';
import { formatDate } from '../../utils/formatDate';

const EventCard = ({ event, onRsvp }) => {
  return (
    <Card>
      <h2 className="text-xl font-bold">{event.title}</h2>
      <p className="text-gray-600 mt-2">{event.description}</p>
      <div className="mt-4 text-sm text-gray-800">
        <p><strong>Starts:</strong> {formatDate(event.startTime)}</p>
        <p><strong>Ends:</strong> {formatDate(event.endTime)}</p>
        <p><strong>Location:</strong> {event.location} {event.isVirtual && '(Virtual)'}</p>
      </div>
      <div className="mt-6 flex space-x-2">
        <Button onClick={() => onRsvp(event.eventId, 'ATTENDING')}>Attend</Button>
        <Button onClick={() => onRsvp(event.eventId, 'MAYBE')} variant="secondary">Maybe</Button>
      </div>
    </Card>
  );
};

export default EventCard;