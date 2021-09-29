import { useEffect, useState } from 'react';
import { requestGetPosts } from '../service/requests';
import { filterIds, filterOnlyNewList } from '../utils/filteringList';

const defaultValue = {
  currPage: 1,
  totalPage: 0,
  totalSize: 0,
  data: [],
};

const useReportStudyLogs = (studyLogs) => {
  const [reportStudyLogData, setReportStudyLogData] = useState(defaultValue);
  const [page, setPage] = useState(1);

  const studyLogIds = filterIds(studyLogs);

  const getPosts = async (currStudyLogIds, currentPage = 1) => {
    try {
      const query = {
        type: 'searchParams',
        data: `ids=${currStudyLogIds.join(',')}&page=${currentPage}`,
      };
      const response = await requestGetPosts(query);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const responseData = await response.json();

      setReportStudyLogData(responseData);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (studyLogs.length === 0) {
      setReportStudyLogData(defaultValue);
    } else {
      const newStudyLogs = filterOnlyNewList(studyLogs, reportStudyLogData.data ?? []);
      const newStudyLogIds = filterIds(newStudyLogs);

      // 모달을 열어 학습로그를 새로 추가한 경우
      if (studyLogIds.length > reportStudyLogData.totalSize) {
        setPage(1);
        getPosts([...newStudyLogIds, ...studyLogIds]);
      } else {
        // 기존의 학습로그 목록에서 삭제만 한 경우
        getPosts(studyLogIds, page);
      }
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [studyLogs]);

  useEffect(() => {
    if (studyLogs.length > 10) {
      getPosts(studyLogIds, page);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page]);

  return { reportStudyLogData, setReportStudyLogData, setPage };
};

export default useReportStudyLogs;
