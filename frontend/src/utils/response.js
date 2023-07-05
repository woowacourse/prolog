const getResponseData = async (response) => {
  const contentType = response.headers.get('content-type');

  if (contentType && contentType.includes('application/json')) {
    return await response.json();
  }

  return await response.text();
};

export { getResponseData };
