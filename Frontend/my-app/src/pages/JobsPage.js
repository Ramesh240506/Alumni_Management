import React, { useState, useEffect } from 'react';
import Layout from '../components/layout/Layout';
import { getOpenJobs } from '../api/jobService';
import Spinner from '../components/ui/Spinner';
import JobCard from '../components/jobs/JobCard'; // <-- UPDATED IMPORT
import JobFilters from '../components/jobs/JobFilters'; // <-- NEW IMPORT

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
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Jobs & Referrals</h1>
        {/* We would link this to a modal or a new page with the JobForm */}
        <button className="bg-indigo-600 text-white font-bold py-2 px-4 rounded-lg hover:bg-indigo-700">
          Post a Job
        </button>
      </div>
      
      <JobFilters /> {/* <-- ADDED FILTERS */}

      {loading ? (
        <Spinner />
      ) : (
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          {jobs.map(job => (
            <JobCard key={job.jobId} job={job} /> // <-- USING NEW JobCard
          ))}
        </div>
      )}
    </Layout>
  );
};

export default JobsPage;