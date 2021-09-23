import { useEffect, useState } from 'react';
import { requestGetPosts } from '../service/requests';
import { filterList } from '../utils/filterList';

const useReportStudyLogs = (studyLogs) => {
  const [currentStudyLogs, setCurrentStudyLogs] = useState(studyLogs);

  useEffect(() => {
    const getPosts = async (newStudyLogs) => {
      try {
        const query = {
          type: 'searchParams',
          data: `ids=${newStudyLogs.map((studyLog) => studyLog.id).join(',')}`,
        };
        const response = await requestGetPosts(query);

        if (!response.ok) {
          throw new Error(response.status);
        }

        const posts = await response.json();

        setCurrentStudyLogs([...currentStudyLogs, ...posts.data]);
      } catch (error) {
        console.error(error);
      }
    };

    if (studyLogs.length === 0) {
      setCurrentStudyLogs([]);
    } else {
      const newStudyLogs = filterList(studyLogs, currentStudyLogs);

      if (newStudyLogs) {
        getPosts(newStudyLogs);
      }
    }
  }, [studyLogs]);

  return [currentStudyLogs, setCurrentStudyLogs];
};

export default useReportStudyLogs;
