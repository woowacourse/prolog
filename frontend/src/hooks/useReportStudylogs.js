import { useEffect, useState } from 'react';
import { filterIds } from '../utils/filteringList';

const defaultValue = {
  data: [],
  currPage: 1,
  totalPage: 0,
  totalSize: 0,
};

const useReportStudyLogs = (studyLogs) => {
  const [selectedStudyLogData, setSelectedStudyLogData] = useState(defaultValue);
  const [page, setPage] = useState(1);

  useEffect(() => {
    if (studyLogs.length > 5) {
      // getPosts(studyLogIds, page);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page]);

  return { selectedStudyLogData, setPage };
};

export default useReportStudyLogs;
