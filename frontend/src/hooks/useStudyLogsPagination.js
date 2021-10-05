import { useEffect, useState } from 'react';

const useStudyLogsPagination = (studyLogs) => {
  const [page, setPage] = useState(1);

  const [reportStudyLogData, setReportStudyLogData] = useState({
    currPage: 1,
    totalPage: 1,
    totalSize: 0,
    data: [],
  });

  useEffect(() => {
    setReportStudyLogData({
      currPage: page,
      totalPage: Math.ceil(studyLogs.length / 10),
      totalSize: studyLogs.length,
      data: studyLogs.slice((page - 1) * 10, page * 10),
    });
  }, [studyLogs, page]);

  return { setPage, reportStudyLogData };
};

export default useStudyLogsPagination;
