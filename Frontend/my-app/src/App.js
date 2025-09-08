import React from 'react';
import { AuthProvider } from './context/AuthContext';
import AppRoutes from './routes/AppRoutes';

function App() {
  return (
    <AuthProvider>
      <div className="bg-gray-100 min-h-screen">
        <AppRoutes />
      </div>
    </AuthProvider>
  );
}

export default App;