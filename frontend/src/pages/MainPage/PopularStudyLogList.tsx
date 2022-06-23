/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useContext, useEffect } from 'react';
import useFetchData from '../../hooks/useFetchData';

import { UserContext } from '../../contexts/UserProvider';
import { Studylog } from '../../models/Studylogs';
import { PopularStudylogListStyle, SectionHeaderGapStyle } from './styles';
import PopularStudylogItem from '../../components/Items/PopularStudylogItem';
import PopularStudyLogListSkeleton from './PopularStudyLogListSkeleton';
import { requestGetPopularStudylogs } from '../../apis/studylogs';

const PopularStudyLogList = (): JSX.Element => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const { response, refetch: fetchPopularStudylogs, isLoading } = useFetchData<{
    data: Studylog[];
  }>({ data: [] }, () => requestGetPopularStudylogs({ accessToken }), { initialFetch: false });

  const studylogs = response.data;

  useEffect(() => {
    fetchPopularStudylogs();
  }, [accessToken]);

  return (
    <section
      css={css`
        width: 100%;
        position: relative;
      `}
    >
      <h2 css={[SectionHeaderGapStyle]}>😎 인기있는 학습로그</h2>
      {isLoading ? (
        <PopularStudyLogListSkeleton />
      ) : (
        <ul css={[PopularStudylogListStyle]}>
          {studylogs?.map((item: Studylog) => (
            <li key={item.id}>
              <PopularStudylogItem item={item} />
            </li>
          ))}
        </ul>
      )}
    </section>
  );
};

export default PopularStudyLogList;
