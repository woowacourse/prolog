import { useEffect, useState } from 'react';

const useStudylogsPagination = (studyLogs) => {
  const [page, setPage] = useState(1);

  const [reportStudylogData, setReportStudylogData] = useState({
    currPage: 1,
    totalPage: 1,
    totalSize: 0,
    data: [],
  });

  useEffect(() => {
    setReportStudylogData({
      currPage: 1,
      totalPage: Math.ceil(studyLogs.length / 10),
      totalSize: studyLogs.length,
      data: studyLogs.slice(0, 10),
    });
  }, [studyLogs]);

  useEffect(() => {
    setReportStudylogData({
      currPage: page,
      totalPage: Math.ceil(studyLogs.length / 10),
      totalSize: studyLogs.length,
      data: studyLogs.slice((page - 1) * 10, page * 10),
    });
  }, [page]);

  return { setPage, reportStudylogData };
};

export default useStudylogsPagination;
