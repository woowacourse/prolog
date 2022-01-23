import { useEffect, useState } from 'react';

const useStudylogsPagination = (Studylogs) => {
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
      totalPage: Math.ceil(Studylogs.length / 10),
      totalSize: Studylogs.length,
      data: Studylogs.slice(0, 10),
    });
  }, [Studylogs]);

  useEffect(() => {
    setReportStudylogData({
      currPage: page,
      totalPage: Math.ceil(Studylogs.length / 10),
      totalSize: Studylogs.length,
      data: Studylogs.slice((page - 1) * 10, page * 10),
    });
  }, [page]);

  return { setPage, reportStudylogData };
};

export default useStudylogsPagination;
