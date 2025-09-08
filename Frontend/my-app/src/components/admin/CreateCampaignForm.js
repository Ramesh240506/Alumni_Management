
import React from 'react';
import Card from '../ui/Card';
import Button from '../ui/Button';

// Simplified form for creating a campaign
const CreateCampaignForm = () => {
  return (
    <Card>
      <h3 className="text-xl font-bold mb-4">Start New Campaign</h3>
      <form className="space-y-4">
        <input placeholder="Campaign Title" className="shadow border rounded-lg w-full py-2 px-3" />
        <input placeholder="Goal Amount" type="number" className="shadow border rounded-lg w-full py-2 px-3" />
        <Button>Launch Campaign</Button>
      </form>
    </Card>
  );
};

export default CreateCampaignForm;