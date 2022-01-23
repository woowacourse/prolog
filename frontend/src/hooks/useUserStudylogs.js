import { useEffect, useState } from 'react';
import { SHOW_ALL_FILTER } from '../pages/ProfilePageNewReport/StudylogModal';
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
        setPage(1);
      }

      const query = {
        type: 'searchParams',
        data: `usernames=${username}&page=${page}${
          currLevelId && currLevelId !== SHOW_ALL_FILTER.id ? `&levels=${currLevelId}` : ''
        }`,
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
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [currLevelId, page]);

  return { studyLogData, setPage };
};

export default useUserStudyLog;
