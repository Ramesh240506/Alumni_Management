
import React, { useState, useEffect } from 'react';
import Layout from '../components/layout/Layout';
import { getEvents, rsvpToEvent } from '../api/eventService';
import Spinner from '../components/ui/Spinner';
import EventCard from '../components/events/EventCard';
import Alert from '../components/ui/Alert';

const EventsPage = () => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState('');

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const data = await getEvents();
        setEvents(data);
      } catch (error) {
        console.error("Failed to fetch events", error);
      } finally {
        setLoading(false);
      }
    };
    fetchEvents();
  }, []);

  const handleRsvp = async (eventId, status) => {
    try {
      await rsvpToEvent(eventId, status);
      setMessage(`Successfully RSVP'd as ${status.toLowerCase()}!`);
      setTimeout(() => setMessage(''), 3000); // Clear message after 3 seconds
    } catch (error) {
      console.error("Failed to RSVP", error);
    }
  };

  return (
    <Layout>
      <h1 className="text-3xl font-bold mb-6">Upcoming Events</h1>
      {message && <Alert message={message} type="success" />}
      {loading ? (
        <Spinner />
      ) : (
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          {events.map(event => (
            <EventCard key={event.eventId} event={event} onRsvp={handleRsvp} />
          ))}
        </div>
      )}
    </Layout>
  );
};

export default EventsPage;