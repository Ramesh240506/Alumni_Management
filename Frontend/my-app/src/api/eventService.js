
import api from './api';

export const getEvents = async () => {
  const response = await api.get('/events');
  return response.data;
};

export const rsvpToEvent = async (eventId, status) => {
  const response = await api.post(`/events/${eventId}/rsvp?status=${status}`);
  return response.data;
};