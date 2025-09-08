
import React from 'react';
import Card from '../ui/Card';
import Button from '../ui/Button';

const JobForm = () => {
  // This would be a Formik form similar to RegisterForm
  return (
    <Card>
      <h2 className="text-2xl font-bold mb-4">Post a Job or Referral</h2>
      <form className="space-y-4">
        <div>
          <label>Job Title</label>
          <input className="shadow border rounded-lg w-full py-2 px-3" />
        </div>
        <div>
          <label>Description</label>
          <textarea className="shadow border rounded-lg w-full py-2 px-3"></textarea>
        </div>
        <Button>Submit Post</Button>
      </form>
    </Card>
  );
};

export default JobForm;