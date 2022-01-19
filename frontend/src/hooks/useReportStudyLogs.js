import { set } from 'local-storage';
import { useEffect, useState } from 'react';
import { requestGetStudyLogs } from '../service/requests';
import { filterIds } from '../utils/filteringList';

const defaultValue = {
  currPage: 1,
  totalPage: 0,
  totalSize: 0,
  data: [],
};

const useReportStudyLogs = (selectedStudyLogs) => {
  const [selectedStudyLogData, setSelectedStudyLogData] = useState(defaultValue);
  const [page, setPage] = useState(1);

  const studyLogIds = filterIds(selectedStudyLogs);

  useEffect(() => {
    if (studyLogIds.length === 0) {
      setSelectedStudyLogData(defaultValue);
    } else {
      // 모달을 열어 학습로그를 새로 추가한 경우
      if (studyLogIds.length > selectedStudyLogData.totalSize) {
        setPage(1);
        getPosts(studyLogIds, 1);
      } else {
        // 기존의 학습로그 목록에서 삭제만 한 경우
        getPosts(studyLogIds, page);
      }
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedStudyLogs]);

  useEffect(() => {
    if (selectedStudyLogs.length > 5) {
      getPosts(studyLogIds, page);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page]);

  const formatSelectedStudyLogData = (response) => {
    const abilityData = selectedStudyLogs.filter(({ abilities }) => abilities.length !== 0);

    const formatData = response.data?.map(({ id, title }) => {
      if (filterIds(abilityData).includes(id)) {
        return {
          id,
          title,
          abilities: abilityData.find((data) => data.id === id).abilities,
        };
      } else {
        return {
          id,
          title,
          abilities: [],
        };
      }
    });

    setSelectedStudyLogData({ ...response, data: formatData });
  };

  const getPosts = async (currStudyLogIds, currentPage = 1) => {
    try {
      const query = {
        type: 'searchParams',
        data: `ids=${currStudyLogIds.join(',')}&page=${currentPage}&size=5`,
      };
      const response = await requestGetStudyLogs(query);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const responseData = await response.json();
      formatSelectedStudyLogData(responseData);
    } catch (error) {
      console.error(error);
    }
  };

  return { selectedStudyLogData, setPage };
};

export default useReportStudyLogs;
