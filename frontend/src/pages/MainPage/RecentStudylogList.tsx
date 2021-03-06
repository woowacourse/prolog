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
    <section>
      <div css={[FlexStyle, JustifyContentSpaceBtwStyle, AlignItemsCenterStyle]}>
        <h2 css={[SectionHeaderGapStyle]}>π μ΅μ  νμ΅λ‘κ·Έ</h2>
        <Link to={PATH.STUDYLOGS}>{`λλ³΄κΈ° >`}</Link>
      </div>
      {studylogs?.length === 0 && 'μμ±λ κΈμ΄ μμ΅λλ€.'}
      {!!studylogs?.length && <StudylogList studylogs={studylogs} />}
    </section>
  );
};

export default RecentStudylogList;
