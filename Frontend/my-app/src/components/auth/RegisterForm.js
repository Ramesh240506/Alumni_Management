
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { registerUser } from '../../api/authService';
import Input from '../ui/Input';
import Button from '../ui/Button';
import Alert from '../ui/Alert';

// This component will be more complex in the future with OTP step
const RegisterForm = () => {
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const navigate = useNavigate();

  // TODO: Fetch this list from an API endpoint later
  const colleges = [
    { id: '9efbec51-8a78-11f0-9a48-b42e99b190a8', name: 'Global Institute of Technology' },
    { id: 'another-uuid-for-arts-college', name: 'National University of Arts' },
  ];

  const formik = useFormik({
    initialValues: {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      aadhaarNumber: '',
      collegeId: '',
    },
    validationSchema: Yup.object({
      firstName: Yup.string().required('First name is required'),
      lastName: Yup.string().required('Last name is required'),
      email: Yup.string().email('Invalid email').required('Email is required'),
      password: Yup.string().min(8, 'Password must be at least 8 characters').required('Password is required'),
      aadhaarNumber: Yup.string().matches(/^\d{12}$/, 'Must be exactly 12 digits').required('Aadhaar number is required'),
      collegeId: Yup.string().required('Please select your college'),
    }),
    onSubmit: async (values, { setSubmitting, resetForm }) => {
      try {
        setError(null);
        setSuccess(null);
        await registerUser(values);
        setSuccess('Registration successful! Please check your device for an OTP to verify.');
        resetForm();
        // Here you would typically redirect to an OTP verification page
        // For now, we'll just show a success message.
      } catch (err) {
        setError(err.response?.data?.message || 'Registration failed.');
      } finally {
        setSubmitting(false);
      }
    },
  });

  return (
    <form onSubmit={formik.handleSubmit} className="space-y-4">
      {error && <Alert message={error} type="error" />}
      {success && <Alert message={success} type="success" />}
      
      <div className="grid grid-cols-2 gap-4">
        <div>
          <label htmlFor="firstName">First Name</label>
          <Input id="firstName" name="firstName" {...formik.getFieldProps('firstName')} />
          {formik.touched.firstName && formik.errors.firstName && <p className="text-red-500 text-xs mt-1">{formik.errors.firstName}</p>}
        </div>
        <div>
          <label htmlFor="lastName">Last Name</label>
          <Input id="lastName" name="lastName" {...formik.getFieldProps('lastName')} />
          {formik.touched.lastName && formik.errors.lastName && <p className="text-red-500 text-xs mt-1">{formik.errors.lastName}</p>}
        </div>
      </div>

      <div>
        <label htmlFor="email">Email</label>
        <Input id="email" name="email" type="email" {...formik.getFieldProps('email')} />
        {formik.touched.email && formik.errors.email && <p className="text-red-500 text-xs mt-1">{formik.errors.email}</p>}
      </div>

      <div>
        <label htmlFor="password">Password</label>
        <Input id="password" name="password" type="password" {...formik.getFieldProps('password')} />
        {formik.touched.password && formik.errors.password && <p className="text-red-500 text-xs mt-1">{formik.errors.password}</p>}
      </div>

      <div>
        <label htmlFor="aadhaarNumber">Aadhaar Number</label>
        <Input id="aadhaarNumber" name="aadhaarNumber" {...formik.getFieldProps('aadhaarNumber')} />
        {formik.touched.aadhaarNumber && formik.errors.aadhaarNumber && <p className="text-red-500 text-xs mt-1">{formik.errors.aadhaarNumber}</p>}
      </div>
      
      <div>
        <label htmlFor="collegeId">College</label>
        <select id="collegeId" name="collegeId" {...formik.getFieldProps('collegeId')} className="shadow border rounded-lg w-full py-2 px-3">
          <option value="" label="Select your college" />
          {colleges.map(college => (
            <option key={college.id} value={college.id}>{college.name}</option>
          ))}
        </select>
        {formik.touched.collegeId && formik.errors.collegeId && <p className="text-red-500 text-xs mt-1">{formik.errors.collegeId}</p>}
      </div>

      <Button type="submit" variant="primary" className="w-full" disabled={formik.isSubmitting}>
        {formik.isSubmitting ? 'Registering...' : 'Register'}
      </Button>
    </form>
  );
};

export default RegisterForm;