const getResponseData = (response) => {
  const contentType = response.headers['content-type'];

  if (contentType && contentType.includes('application/json')) {
    return response.data;
  }

  return response.data.toString();
};

export { getResponseData };
