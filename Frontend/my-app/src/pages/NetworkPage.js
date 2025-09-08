import React, { useState, useEffect } from 'react';
import Layout from '../components/layout/Layout';
import AlumniMap from '../components/network/AlumniMap';
import AlumniCard from '../components/network/AlumniCard';
import { getNetworkAlumni } from '../api/alumniService';
import Spinner from '../components/ui/Spinner';

const NetworkPage = () => {
  const [alumni, setAlumni] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAlumni = async () => {
      try {
        const data = await getNetworkAlumni();
        setAlumni(data);
      } catch (error) {
        console.error("Failed to fetch alumni data", error);
      } finally {
        setLoading(false);
      }
    };
    fetchAlumni();
  }, []);

  return (
    <Layout>
      <h1 className="text-3xl font-bold mb-6">Alumni Global Network</h1>
      {loading ? <Spinner /> : (
        <>
          <AlumniMap alumni={alumni} />
          <h2 className="text-2xl font-bold mt-12 mb-6">Directory</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
            {alumni.map(alum => <AlumniCard key={alum.alumniUserId} alumnus={alum} />)}
          </div>
        </>
      )}
    </Layout>
  );
};

export default NetworkPage;