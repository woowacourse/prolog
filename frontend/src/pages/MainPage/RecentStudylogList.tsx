/** @jsxImportSource @emotion/react */

import { Link } from 'react-router-dom';

import StudylogList from '../../components/Lists/StudylogList';
import { PATH } from '../../constants';

import {
  FlexStyle,
  AlignItemsCenterStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import { SectionHeaderGapStyle } from './styles';

const RecentStudylogList = ({ studylogs }: { studylogs: Prolog.Studylog[] }) => {
  return (
    <section>
      <div css={[FlexStyle, JustifyContentSpaceBtwStyle, AlignItemsCenterStyle]}>
        <h2 css={[SectionHeaderGapStyle]}>📚 최신 학습로그</h2>
        <Link to={PATH.STUDYLOG}>{`더보기 >`}</Link>
      </div>
      {studylogs?.length === 0 && '작성된 글이 없습니다.'}
      {!!studylogs?.length && <StudylogList studylogs={studylogs} />}
    </section>
  );
};

export default RecentStudylogList;
