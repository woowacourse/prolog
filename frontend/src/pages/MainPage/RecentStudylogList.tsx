/** @jsxImportSource @emotion/react */

import { Link } from 'react-router-dom';

import StudylogList from '../../components/Lists/StudylogList';
import { PATH } from '../../enumerations/path';
import { Studylog } from '../../models/Studylogs';

import {
  FlexStyle,
  AlignItemsCenterStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import { SectionHeaderGapStyle } from './styles';

const RecentStudylogList = ({ studylogs }: { studylogs: Studylog[] }) => {
  return (
    <section style={{ marginTop: '3rem' }}>
      <div css={[FlexStyle, JustifyContentSpaceBtwStyle, AlignItemsCenterStyle]}>
        <h2 css={[SectionHeaderGapStyle]}>📚 최신 학습로그</h2>
        <Link to={PATH.STUDYLOGS}>{`더보기 >`}</Link>
      </div>
      {studylogs?.length === 0 && '작성된 글이 없습니다.'}
      {!!studylogs?.length && <StudylogList studylogs={studylogs} />}
    </section>
  );
};

export default RecentStudylogList;
