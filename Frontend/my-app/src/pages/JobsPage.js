
import React, { useState, useEffect } from 'react';
import Layout from '../components/layout/Layout';
import { getOpenJobs } from '../api/jobService';
import Card from '../components/ui/Card';
import Spinner from '../components/ui/Spinner';

const JobsPage = () => {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        const data = await getOpenJobs();
        setJobs(data);
      } catch (error) {
        console.error("Failed to fetch jobs", error);
      } finally {
        setLoading(false);
      }
    };
    fetchJobs();
  }, []);

  return (
    <Layout>
      <h1 className="text-3xl font-bold mb-6">Jobs & Referrals</h1>
      {loading ? (
        <Spinner />
      ) : (
        <div className="grid md:grid-cols-2 gap-6">
          {jobs.map(job => (
            <Card key={job.jobId}>
              <h2 className="text-xl font-bold">{job.title}</h2>
              <p className="text-gray-600">{job.company.name}</p>
              <p className="text-sm text-gray-500 mt-2">{job.location}</p>
            </Card>
          ))}
        </div>
      )}
    </Layout>
  );
};

export default JobsPage;