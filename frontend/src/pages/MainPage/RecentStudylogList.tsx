/** @jsxImportSource @emotion/react */

import { useContext, useEffect } from 'react';
import { Link } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';
import StudylogList from '../../components/Lists/StudylogList';
import { PATH } from '../../enumerations/path';
import useFetchData from '../../hooks/useFetchData';
import { Studylog } from '../../models/Studylogs';
import { requestGetStudylogs } from '../../service/requests';

import {
  FlexStyle,
  AlignItemsCenterStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import { SectionHeaderGapStyle } from './styles';
import RecentStudylogListSkeleton from './RecentStudylogListSkeleton';

const RecentStudylogList = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const { response, isLoading, refetch: fetchRecentStudylogs } = useFetchData<{
    data: Studylog[];
  }>(
    { data: [] },
    () => requestGetStudylogs({ query: { type: 'searchParams', data: 'size=3' }, accessToken }),
    { initialFetch: false }
  );
  const studylogs = response.data;

  useEffect(() => {
    fetchRecentStudylogs();
  }, [accessToken]);

  return (
    <section>
      <div css={[FlexStyle, JustifyContentSpaceBtwStyle, AlignItemsCenterStyle]}>
        <h2 css={[SectionHeaderGapStyle]}>📚 최신 학습로그</h2>
        <Link to={PATH.STUDYLOGS}>{`더보기 >`}</Link>
      </div>
      {isLoading ? <RecentStudylogListSkeleton /> : <StudylogList studylogs={studylogs} />}
    </section>
  );
};

export default RecentStudylogList;
