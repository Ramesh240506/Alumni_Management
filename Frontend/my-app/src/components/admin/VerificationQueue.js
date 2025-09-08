
import React, { useState, useEffect } from 'react';
import { getPendingVerifications, approveRecord } from '../../api/adminService';
import Card from '../ui/Card';
import Button from '../ui/Button';
import Spinner from '../ui/Spinner';

const VerificationQueue = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRequests = async () => {
      const data = await getPendingVerifications();
      setRequests(data);
      setLoading(false);
    };
    fetchRequests();
  }, []);

  if (loading) return <Spinner />;

  return (
    <Card>
      <h2 className="text-2xl font-bold mb-4">Pending Verifications</h2>
      <div className="space-y-4">
        {requests.length > 0 ? requests.map(req => (
          <div key={req.recordId} className="p-4 border rounded-lg flex justify-between items-center">
            <div>
              <p className="font-semibold">{req.alumnus.name}</p>
              <p className="text-sm text-gray-600">{req.degreeName}</p>
            </div>
            <div className="space-x-2">
              <Button onClick={() => approveRecord(req.recordId)}>Approve</Button>
              <Button variant="secondary">Reject</Button>
            </div>
          </div>
        )) : <p>No pending verifications.</p>}
      </div>
    </Card>
  );
};

export default VerificationQueue;