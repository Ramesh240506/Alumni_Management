
import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import useAuth from '../../hooks/useAuth';
import { updateMyProfile } from '../../api/alumniService';
import Input from '../ui/Input';
import Button from '../ui/Button';
import Alert from '../ui/Alert';

const EditProfileForm = () => {
  const { user } = useAuth();
  const [success, setSuccess] = useState('');
  const [error, setError] = useState('');

  const formik = useFormik({
    initialValues: {
      phone: user?.phone || '',
    },
    validationSchema: Yup.object({
      phone: Yup.string().matches(/^\+?[0-9. ()-]{7,25}$/, 'Invalid phone number format'),
    }),
    onSubmit: async (values, { setSubmitting }) => {
      try {
        setError('');
        setSuccess('');
        await updateMyProfile(values);
        setSuccess('Profile updated successfully!');
        // Ideally, you would also update the user in the AuthContext here.
      } catch (err) {
        setError('Failed to update profile.');
      } finally {
        setSubmitting(false);
      }
    },
  });

  return (
    <form onSubmit={formik.handleSubmit} className="space-y-4">
      {success && <Alert message={success} type="success" />}
      {error && <Alert message={error} type="error" />}
      <div>
        <label htmlFor="phone" className="block text-sm font-medium text-gray-700">Phone Number</label>
        <Input id="phone" name="phone" {...formik.getFieldProps('phone')} />
        {formik.touched.phone && formik.errors.phone && <p className="text-red-500 text-xs mt-1">{formik.errors.phone}</p>}
      </div>
      <Button type="submit" disabled={formik.isSubmitting}>
        {formik.isSubmitting ? 'Saving...' : 'Save Changes'}
      </Button>
    </form>
  );
};

export default EditProfileForm;