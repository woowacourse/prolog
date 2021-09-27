import { useEffect, useState } from 'react';
import { requestGetPosts } from '../service/requests';

const defaultValue = {
  totalSize: 0,
  totalPage: 1,
  currPage: 1,
  data: [],
  levelId: 0,
};

const useUserStudyLog = ({ levels, username }) => {
  const [selectedLevelName, setSelectedLevelName] = useState('');
  const [postData, setPostData] = useState(defaultValue);
  const [page, setPage] = useState(1);

  const getPosts = async (page = 1) => {
    try {
      const selectedLevelId = levels.find((level) => level.name === selectedLevelName)?.id;

      if (selectedLevelId) {
        if (selectedLevelId !== postData.levelId) {
          page = 1;
        }

        const query = {
          type: 'searchParams',
          data: `levels=${selectedLevelId}&usernames=${username}&page=${page}`,
        };

        const response = await requestGetPosts(query);

        if (!response.ok) {
          throw new Error(response.status);
        }

        const responseData = await response.json();

        if (postData.data && postData.levelId === selectedLevelId) {
          responseData.data = [...postData.data, ...responseData.data];
        }

        setPostData({ ...responseData, levelId: selectedLevelId });
      }
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (levels) {
      getPosts(page);
    }
  }, [levels, selectedLevelName, page]);

  return { selectedLevelName, setSelectedLevelName, postData, setPage };
};

export default useUserStudyLog;
