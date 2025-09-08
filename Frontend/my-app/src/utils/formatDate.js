
export const formatDate = (dateString) => {
  if (!dateString) return 'N/A';
  
  try {
    const options = {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: 'numeric',
      minute: '2-digit',
      hour12: true,
    };
    return new Intl.DateTimeFormat('en-US', options).format(new Date(dateString));
  } catch (error) {
    console.error("Failed to format date:", dateString, error);
    return dateString;
  }
};