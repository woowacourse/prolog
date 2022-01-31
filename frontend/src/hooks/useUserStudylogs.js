import { useEffect, useState } from 'react';
import { SHOW_ALL_FILTER } from '../pages/ProfilePageNewReport/StudylogModal';
import { requestGetStudylogs } from '../service/requests';

const defaultValue = {
  totalSize: 0,
  totalPage: 1,
  currPage: 1,
  data: [],
  levelId: 0,
};

const useUserStudylog = ({ levelId: currLevelId, username }) => {
  const [studylogData, setStudylogData] = useState(defaultValue);
  const [page, setPage] = useState(1);

  const getStudylogs = async (page = 1) => {
    try {
      if (currLevelId !== studylogData.levelId) {
        setPage(1);
      }

      const query = {
        type: 'searchParams',
        data: `usernames=${username}&page=${page}${
          currLevelId && currLevelId !== SHOW_ALL_FILTER.id ? `&levels=${currLevelId}` : ''
        }`,
      };

      const response = await requestGetStudylogs(query);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const responseData = await response.json();

      if (studylogData.data && studylogData.levelId === currLevelId) {
        responseData.data = [...studylogData.data, ...responseData.data];
      }

      setStudylogData({ ...responseData, levelId: currLevelId });
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (currLevelId) {
      getStudylogs(page);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [currLevelId, page]);

  return { studylogData, setPage };
};

export default useUserStudylog;
