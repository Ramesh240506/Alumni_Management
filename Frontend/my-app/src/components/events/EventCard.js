import React from 'react';
import Card from '../ui/Card';
import { formatDate } from '../../utils/formatDate';
import RsvpButton from './RsvpButton'; // <-- NEW IMPORT

const EventCard = ({ event, onRsvp }) => {
  return (
    <Card>
      <h2 className="text-xl font-bold">{event.title}</h2>
      <p className="text-gray-600 mt-2 line-clamp-3">{event.description}</p>
      <div className="mt-4 text-sm text-gray-800">
        <p><strong>Starts:</strong> {formatDate(event.startTime)}</p>
        <p><strong>Location:</strong> {event.location} {event.isVirtual && '(Virtual)'}</p>
      </div>
      <RsvpButton eventId={event.eventId} onRsvp={onRsvp} /> {/* <-- USING NEW BUTTON COMPONENT */}
    </Card>
  );
};

export default EventCard;