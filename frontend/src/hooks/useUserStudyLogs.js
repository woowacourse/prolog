import { useEffect, useState } from 'react';
import { SHOW_ALL_STUDYLOGS } from '../pages/AbilityPage/StudyLogModal';
import { requestGetStudyLogs } from '../service/requests';

const DEFAULT_STUDYLOG_DATA = {
  totalSize: 0,
  totalPage: 1,
  currPage: 1,
  data: [],
  levelId: 0,
};

const useUserStudyLog = ({ levelId: currLevelId, username }) => {
  const [studyLogData, setStudyLogData] = useState(DEFAULT_STUDYLOG_DATA);
  const [page, setPage] = useState(1);

  useEffect(() => {
    if (currLevelId) {
      getStudyLogs(page);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [currLevelId, page]);

  /**
   * 기존의 스터디로그 데이터와 새로 응답받은 데이터를 합치는 메서드
   * 기존에 데이터가 있을 때, 레벨을 이동했을 때를 분리해서 데이터를 저장
   */
  const combineStudyLogData = (response) => {
    if (studyLogData.data && studyLogData.levelId === currLevelId) {
      setStudyLogData((prevStudyLogData) => ({
        ...prevStudyLogData,
        data: [...studyLogData.data, ...response.data],
      }));
    } else {
      setStudyLogData({ ...response, levelId: currLevelId });
    }
  };

  /**
   * 선택한 레벨에서 작성한 학습로그를 가져오도록하는 메서드
   * - isSelectedLevel : 선택된 레벨이 있는지, 아니면 전체보기인건지
   * - query : 해당 레벨의 학습로그를 10개씩 가져오도록 함
   * - responseData : currPage, data, totalPage, totalSize로 이루어져있는 객체
   */
  const getStudyLogs = async (page = 1) => {
    try {
      if (currLevelId !== studyLogData.levelId) {
        setPage(1);
      }

      const isSelectedLevel = currLevelId && currLevelId !== SHOW_ALL_STUDYLOGS.id;
      const query = {
        type: 'searchParams',
        data: `usernames=${username}&page=${page}${
          isSelectedLevel ? `&levels=${currLevelId}` : ''
        }`,
      };

      const response = await requestGetStudyLogs(query);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const responseData = await response.json();
      combineStudyLogData(responseData);
    } catch (error) {
      console.error(error);
    }
  };

  return { studyLogData, setPage };
};

export default useUserStudyLog;
