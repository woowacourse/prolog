import { useEffect, useState } from 'react';

const useStudylogsPagination = (studylogs) => {
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
      totalPage: Math.ceil(studylogs.length / 10),
      totalSize: studylogs.length,
      data: studylogs.slice(0, 10),
    });
  }, [studylogs]);

  useEffect(() => {
    setReportStudylogData({
      currPage: page,
      totalPage: Math.ceil(studylogs.length / 10),
      totalSize: studylogs.length,
      data: studylogs.slice((page - 1) * 10, page * 10),
    });
  }, [page]);

  return { setPage, reportStudylogData };
};

export default useStudylogsPagination;
