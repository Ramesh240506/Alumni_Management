
import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import Card from '../ui/Card';
import Input from '../ui/Input';
import Button from '../ui/Button';

// Assume you have an adminService.createEvent function
const CreateEventForm = () => {
  const formik = useFormik({
    initialValues: { title: '', description: '', location: '', startTime: '', endTime: '' },
    validationSchema: Yup.object({
      title: Yup.string().required('Title is required'),
      location: Yup.string().required('Location is required'),
      startTime: Yup.date().required('Start time is required'),
      endTime: Yup.date().required('End time is required'),
    }),
    onSubmit: (values) => {
      alert(JSON.stringify(values, null, 2));
      // Call adminService.createEvent(values);
    },
  });

  return (
    <Card>
      <h3 className="text-xl font-bold mb-4">Create New Event</h3>
      <form onSubmit={formik.handleSubmit} className="space-y-4">
        <Input name="title" placeholder="Event Title" {...formik.getFieldProps('title')} />
        <textarea name="description" placeholder="Description" {...formik.getFieldProps('description')} className="shadow border rounded-lg w-full py-2 px-3"></textarea>
        <Input name="location" placeholder="Location" {...formik.getFieldProps('location')} />
        <Input name="startTime" type="datetime-local" {...formik.getFieldProps('startTime')} />
        <Input name="endTime" type="datetime-local" {...formik.getFieldProps('endTime')} />
        <Button type="submit">Create Event</Button>
      </form>
    </Card>
  );
};

export default CreateEventForm;