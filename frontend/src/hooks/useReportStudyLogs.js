import { useEffect, useState } from 'react';
import { requestGetStudylogs } from '../service/requests';
import { filterIds } from '../utils/filteringList';

const defaultValue = {
  currPage: 1,
  totalPage: 0,
  totalSize: 0,
  data: [],
};

const useReportStudylogs = (studyLogs) => {
  const [reportStudylogData, setReportStudylogData] = useState(defaultValue);
  const [page, setPage] = useState(1);

  const studyLogIds = filterIds(studyLogs);

  const getStudylogs = async (currStudylogIds, currentPage = 1) => {
    try {
      const query = {
        type: 'searchParams',
        data: `ids=${currStudylogIds.join(',')}&page=${currentPage}`,
      };
      const response = await requestGetStudylogs(query);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const responseData = await response.json();

      setReportStudylogData(responseData);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (studyLogs.length === 0) {
      setReportStudylogData(defaultValue);
    } else {
      // 모달을 열어 학습로그를 새로 추가한 경우
      if (studyLogIds.length > reportStudylogData.totalSize) {
        setPage(1);
        getStudylogs(studyLogIds, 1);
      } else {
        // 기존의 학습로그 목록에서 삭제만 한 경우
        getStudylogs(studyLogIds, page);
      }
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [studyLogs]);

  useEffect(() => {
    if (studyLogs.length > 10) {
      getStudylogs(studyLogIds, page);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page]);

  return { reportStudylogData, setReportStudylogData, setPage };
};

export default useReportStudylogs;
