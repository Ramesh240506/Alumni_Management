
import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { loginUser } from '../../api/authService';
import useAuth from '../../hooks/useAuth';
import Input from '../ui/Input';
import Button from '../ui/Button';
import Alert from '../ui/Alert';

const LoginForm = () => {
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const auth = useAuth();

  const formik = useFormik({
    initialValues: {
      email: '',
      password: '',
    },
    validationSchema: Yup.object({
      email: Yup.string().email('Invalid email address').required('Email is required'),
      password: Yup.string().required('Password is required'),
    }),
    onSubmit: async (values, { setSubmitting }) => {
      try {
        setError(null);
        const data = await loginUser(values);
        auth.login(data.token);
        navigate('/dashboard'); // Redirect to dashboard on successful login
      } catch (err) {
        setError(err.response?.data?.message || 'An unexpected error occurred.');
      } finally {
        setSubmitting(false);
      }
    },
  });

  return (
    <form onSubmit={formik.handleSubmit} className="space-y-6">
      {error && <Alert message={error} />}
      <div>
        <label htmlFor="email" className="block text-sm font-medium text-gray-700">
          Email Address
        </label>
        <Input
          id="email"
          name="email"
          type="email"
          onChange={formik.handleChange}
          onBlur={formik.handleBlur}
          value={formik.values.email}
          placeholder="you@example.com"
        />
        {formik.touched.email && formik.errors.email ? (
          <p className="text-red-500 text-xs mt-1">{formik.errors.email}</p>
        ) : null}
      </div>
      <div>
        <label htmlFor="password" className="block text-sm font-medium text-gray-700">
          Password
        </label>
        <Input
          id="password"
          name="password"
          type="password"
          onChange={formik.handleChange}
          onBlur={formik.handleBlur}
          value={formik.values.password}
          placeholder="********"
        />
        {formik.touched.password && formik.errors.password ? (
          <p className="text-red-500 text-xs mt-1">{formik.errors.password}</p>
        ) : null}
      </div>
      <div>
        <Button type="submit" variant="primary" className="w-full" disabled={formik.isSubmitting}>
          {formik.isSubmitting ? 'Logging in...' : 'Login'}
        </Button>
      </div>
      <p className="text-center text-sm text-gray-600">
        Don't have an account?{' '}
        <Link to="/register" className="font-medium text-indigo-600 hover:text-indigo-500">
          Register here
        </Link>
      </p>
    </form>
  );
};

export default LoginForm;