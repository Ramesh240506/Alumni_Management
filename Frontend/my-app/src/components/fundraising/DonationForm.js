
import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import Button from '../ui/Button';
import Input from '../ui/Input';
import { makeDonation } from '../../api/fundraisingService';

const DonationForm = ({ campaignId, onSuccess }) => {
  const formik = useFormik({
    initialValues: { amount: '', paymentGatewayToken: 'tok_visa' }, // Placeholder token
    validationSchema: Yup.object({
      amount: Yup.number().min(1, 'Amount must be at least $1').required('Amount is required'),
    }),
    onSubmit: async (values, { setSubmitting }) => {
      try {
        await makeDonation({ ...values, campaignId, isAnonymous: false });
        onSuccess('Thank you for your generous donation!');
      } catch (error) {
        console.error('Donation failed', error);
      } finally {
        setSubmitting(false);
      }
    },
  });

  return (
    <form onSubmit={formik.handleSubmit} className="mt-4 space-y-4">
      <Input
        name="amount"
        type="number"
        placeholder="Enter amount"
        {...formik.getFieldProps('amount')}
      />
      {formik.touched.amount && formik.errors.amount && <p className="text-red-500 text-xs mt-1">{formik.errors.amount}</p>}
      
      {/* In a real app, this would be a Stripe/PayPal element, not an input */}
      <input type="hidden" name="paymentGatewayToken" {...formik.getFieldProps('paymentGatewayToken')} />

      <Button type="submit" className="w-full" disabled={formik.isSubmitting}>
        {formik.isSubmitting ? 'Processing...' : 'Donate Now'}
      </Button>
    </form>
  );
};

export default DonationForm;