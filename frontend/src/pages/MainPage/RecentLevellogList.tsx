/** @jsxImportSource @emotion/react */

import { Link } from 'react-router-dom';

import LevellogList from '../../components/Lists/LevellogList';
import { PATH } from '../../constants';
import { LevellogResponse } from '../../models/Levellogs';


import {
  FlexStyle,
  AlignItemsCenterStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import { SectionHeaderGapStyle } from './styles';

const RecentLevellogList = ({ levellogs }: { levellogs: LevellogResponse[] }) => {
  return (
    <section>
      <div css={[FlexStyle, JustifyContentSpaceBtwStyle, AlignItemsCenterStyle]}>
        <h2 css={[SectionHeaderGapStyle]}>💬 최신 레벨로그</h2>
        <Link to={PATH.LEVELLOG}>{`더보기 >`}</Link>
      </div>
      {levellogs?.length === 0 && '작성된 글이 없습니다.'}
      {!!levellogs?.length && <LevellogList levellogs={levellogs} />}
    </section>
  );
};

export default RecentLevellogList;
