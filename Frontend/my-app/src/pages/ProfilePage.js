import React from 'react';
import Layout from '../components/layout/Layout';
import useAuth from '../hooks/useAuth';
import ProfileHeader from '../components/profile/ProfileHeader';
import WorkExperienceCard from '../components/profile/WorkExperienceCard';
import AcademicRecordCard from '../components/profile/AcademicRecordCard';
import Spinner from '../components/ui/Spinner';

const ProfilePage = () => {
  const { user, loading } = useAuth();

  if (loading || !user) {
    return <Layout><Spinner /></Layout>;
  }

  return (
    <Layout>
      <ProfileHeader user={user} />
      <div className="mt-8 grid md:grid-cols-2 gap-8">
        <div>
          <h3 className="text-2xl font-bold mb-4">Work Experience</h3>
          <div className="space-y-4">
            {user.workExperiences?.length > 0 ? (
              user.workExperiences.map(exp => <WorkExperienceCard key={exp.experienceId} experience={exp} />)
            ) : <p>No work experience added yet.</p>}
          </div>
        </div>
        <div>
          <h3 className="text-2xl font-bold mb-4">Academic Records</h3>
          <div className="space-y-4">
             {user.academicRecords?.length > 0 ? (
              user.academicRecords.map(rec => <AcademicRecordCard key={rec.recordId} record={rec} />)
            ) : <p>No academic records found.</p>}
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default ProfilePage;