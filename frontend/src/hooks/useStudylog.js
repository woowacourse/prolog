import { useState } from 'react';
import { ERROR_MESSAGE } from '../constants/message';
import {
  requestEditStudylog,
  requestGetStudylog,
  requestGetStudylogs,
  requestDeleteStudylog,
  requestPostStudylog,
} from '../service/requests';

const useStudylog = (defaultValue) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  const getAllData = async (query, accessToken) => {
    try {
      const response = await requestGetStudylogs(query, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }
      const json = await response.json();

      setResponse({
        ...json,
        data: json.data.map(
          ({
            id,
            author,
            content,
            mission,
            title,
            tags,
            createdAt,
            updatedAt,
            read,
            scrap,
            viewCount,
          }) => ({
            id,
            author,
            content,
            mission,
            title,
            tags,
            createdAt,
            updatedAt,
            isRead: read,
            isScrapped: scrap,
            viewCount,
          })
        ),
      });
      setSuccess(true);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    }
  };

  const getData = async (studylogId, accessToken) => {
    try {
      const response = await requestGetStudylog(studylogId, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }
      const json = await response.json();

      setResponse(json);
      setSuccess(true);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(ERROR_MESSAGE[errorResponse.code] ?? ERROR_MESSAGE.DEFAULT);
    }
  };

  const postData = async (data, accessToken) => {
    try {
      const response = await requestPostStudylog(data, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      setResponse(response);
      setSuccess(true);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      setError(ERROR_MESSAGE[errorResponse.code] ?? ERROR_MESSAGE.DEFAULT);

      return ERROR_MESSAGE[errorResponse.code] ?? ERROR_MESSAGE.DEFAULT;
    }
  };

  const editData = async (studylogId, data, accessToken) => {
    try {
      const response = await requestEditStudylog(studylogId, data, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      setResponse(response);
      setSuccess(true);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(ERROR_MESSAGE[errorResponse.code] ?? ERROR_MESSAGE.DEFAULT);

      return ERROR_MESSAGE[errorResponse.code] ?? ERROR_MESSAGE.DEFAULT;
    }
  };

  const deleteData = async (studylogId, accessToken) => {
    try {
      const response = await requestDeleteStudylog(studylogId, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      setResponse(response);
      setSuccess(true);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(ERROR_MESSAGE[errorResponse.code] ?? ERROR_MESSAGE.DEFAULT);

      return ERROR_MESSAGE[errorResponse.code] ?? ERROR_MESSAGE.DEFAULT;
    }
  };

  return { response, error, success, getAllData, getData, postData, editData, deleteData };
};

export default useStudylog;
