export const getLocalStorageItem = (key) => {
  const item = localStorage.getItem(key);

  if (!item) {
    return null;
  }

  try {
    const json = JSON.parse(item);

    return json;
  } catch (error) {
    return null;
  }
};
