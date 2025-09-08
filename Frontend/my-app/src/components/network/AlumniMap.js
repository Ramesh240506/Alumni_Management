
import React from 'react';
import { GoogleMap, useLoadScript, Marker } from '@react-google-maps/api';

const mapContainerStyle = {
  width: '100%',
  height: '500px',
  borderRadius: '0.5rem',
};

const center = {
  lat: 20.5937, // Centered on India
  lng: 78.9629,
};

const AlumniMap = ({ alumni }) => {
  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY, // IMPORTANT: Add your key to .env
  });

  if (loadError) return "Error loading maps";
  if (!isLoaded) return "Loading Maps...";

  return (
    <GoogleMap
      mapContainerStyle={mapContainerStyle}
      zoom={4}
      center={center}
    >
      {alumni.map((alum) => (
        <Marker 
          key={alum.alumniUserId} 
          position={{ lat: alum.lat, lng: alum.lng }}
          title={`${alum.firstName} ${alum.lastName}`}
        />
      ))}
    </GoogleMap>
  );
};

export default AlumniMap;