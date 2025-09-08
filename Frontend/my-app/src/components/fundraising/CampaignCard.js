import React from 'react';
import Card from '../ui/Card';
import Button from '../ui/Button';

// The card now accepts an onDonateClick prop
const CampaignCard = ({ campaign, onDonateClick }) => {
  const percentage = campaign.goalAmount > 0 ? (campaign.currentAmount / campaign.goalAmount) * 100 : 0;

  return (
    <Card>
      <h2 className="text-xl font-bold">{campaign.title}</h2>
      <p className="text-gray-600 mt-2 line-clamp-3">{campaign.description}</p>
      <div className="mt-4">
        <div className="w-full bg-gray-200 rounded-full h-2.5">
          <div className="bg-indigo-600 h-2.5 rounded-full" style={{ width: `${Math.min(percentage, 100)}%` }}></div>
        </div>
        <div className="flex justify-between text-sm font-medium mt-2">
          <span>Raised: ${Number(campaign.currentAmount).toLocaleString()}</span>
          <span>Goal: ${Number(campaign.goalAmount).toLocaleString()}</span>
        </div>
      </div>
      <div className="mt-6">
        {/* The button now calls the handler passed from the parent page */}
        <Button onClick={onDonateClick} className="w-full">Donate Now</Button>
      </div>
    </Card>
  );
};

export default CampaignCard;