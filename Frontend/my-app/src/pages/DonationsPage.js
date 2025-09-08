import React, { useState, useEffect } from 'react';
import Layout from '../components/layout/Layout';
import { getCampaigns } from '../api/fundraisingService';
import Spinner from '../components/ui/Spinner';
import CampaignCard from '../components/fundraising/CampaignCard';
import Modal from '../components/ui/Modal';
import DonationForm from '../components/fundraising/DonationForm';
import Alert from '../components/ui/Alert';

const DonationsPage = () => {
  const [campaigns, setCampaigns] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCampaign, setSelectedCampaign] = useState(null);
  const [successMessage, setSuccessMessage] = useState('');

  useEffect(() => {
    const fetchCampaigns = async () => {
      try {
        const data = await getCampaigns();
        setCampaigns(data);
      } catch (error) {
        console.error("Failed to fetch campaigns", error);
      } finally {
        setLoading(false);
      }
    };
    fetchCampaigns();
  }, []);

  const handleDonateSuccess = (message) => {
    setSuccessMessage(message);
    setSelectedCampaign(null); // Close the modal on success
    setTimeout(() => setSuccessMessage(''), 4000);
  };

  return (
    <Layout>
      <h1 className="text-3xl font-bold mb-6">Give Back</h1>
      <p className="text-lg text-gray-600 mb-8">Support your alma mater by contributing to these active campaigns.</p>
      {successMessage && <Alert message={successMessage} type="success" className="mb-6" />}
      
      {loading ? (
        <Spinner />
      ) : (
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          {campaigns.map(campaign => (
            // Pass the onClick handler to the card
            <CampaignCard 
              key={campaign.campaignId} 
              campaign={campaign} 
              onDonateClick={() => setSelectedCampaign(campaign)} 
            />
          ))}
        </div>
      )}

      {/* The Modal for the donation form */}
      <Modal isOpen={!!selectedCampaign} onClose={() => setSelectedCampaign(null)}>
        {selectedCampaign && (
            <>
                <h2 className="text-2xl font-bold mb-4">Donate to {selectedCampaign.title}</h2>
                <DonationForm campaignId={selectedCampaign.campaignId} onSuccess={handleDonateSuccess} />
            </>
        )}
      </Modal>
    </Layout>
  );
};

export default DonationsPage;