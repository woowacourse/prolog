import { useEffect, useState } from 'react';
import { requestGetPosts } from '../service/requests';
import { filterList } from '../utils/filterList';

const useReportStudyLogs = (studyLogs) => {
  const [currentStudyLogs, setCurrentStudyLogs] = useState(studyLogs);

  useEffect(() => {
    const newStudyLogs = filterList(studyLogs, currentStudyLogs);

    const getPosts = async () => {
      if (newStudyLogs) {
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
      }
    };

    if (studyLogs?.length) {
      getPosts();
    } else {
      return setCurrentStudyLogs([]);
    }
  }, [studyLogs]);

  return [currentStudyLogs, setCurrentStudyLogs];
};

export default useReportStudyLogs;
