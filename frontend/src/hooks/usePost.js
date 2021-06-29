import { useState } from 'react';
import {
  requestEditPost,
  requestGetPost,
  requestGetPosts,
  requestDeletePost,
  requestGetMyPosts,
} from '../service/requests';

const usePost = (defaultValue) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const getAllData = async () => {
    try {
      const response = await requestGetPosts();

      if (!response.ok) {
        throw new Error(response.status);
      }

      const json = await response.json();

      setResponse(json);
    } catch (error) {
      console.error(error);
      setError(error);
    }
  };

  const getData = async (postId) => {
    try {
      const response = await requestGetPost(postId);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const json = await response.json();

      setResponse(json);
    } catch (error) {
      console.error(error);
      setError(error);
    }
  };

  const editData = async (postId, data, accessToken) => {
    try {
      const response = await requestEditPost(postId, data, accessToken);

      if (!response.ok) {
        setError(response.status);
      }

      setResponse(response);
    } catch (error) {
      console.error(error);
      setError(error);
    }
  };

  const deleteData = async (postId, accessToken) => {
    try {
      const response = await requestDeletePost(postId, accessToken);

      if (!response.ok) {
        setError(response.status);
      }

      setResponse(response);
    } catch (error) {
      console.error(error);
      setError(error);
    }
  };

  return [response, error, getAllData, getData, editData, deleteData];
};

export default usePost;
