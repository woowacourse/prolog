import { useEffect, useState } from 'react';
import { requestGetPosts } from '../service/requests';

const defaultValue = {
  totalSize: 0,
  totalPage: 1,
  currPage: 1,
  data: [],
  levelId: 0,
};

const useUserStudyLog = ({ levelId: currLevelId, username }) => {
  const [studyLogData, setStudyLogData] = useState(defaultValue);
  const [page, setPage] = useState(1);

  const getPosts = async (page = 1) => {
    try {
      if (currLevelId !== studyLogData.levelId) {
        setPage((page) => (page = 1));
      }

      const query = {
        type: 'searchParams',
        data: `levels=${currLevelId}&usernames=${username}&page=${page}`,
      };

      const response = await requestGetPosts(query);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const responseData = await response.json();

      if (studyLogData.data && studyLogData.levelId === currLevelId) {
        responseData.data = [...studyLogData.data, ...responseData.data];
      }

      setStudyLogData({ ...responseData, levelId: currLevelId });
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (currLevelId) {
      getPosts(page);
    }
  }, [currLevelId, page]);

  return { studyLogData, setPage };
};

export default useUserStudyLog;
