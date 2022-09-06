/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { PATH } from '../../constants';

import LevellogItem from '../Items/LevellogItem';
import { NoDefaultHoverLink } from './LevellogList.styles';

const LevellogList = ({ levellogs }) => {
  return (
    <ul
      css={css`
        > li:not(:last-child) {
          margin-bottom: 1.6rem;
        }
      `}
    >
      {levellogs.map((levellog) => (
        <li key={levellog.id}>
          <NoDefaultHoverLink to={`${PATH.LEVELLOG}/${levellog.id}`}>
            <LevellogItem levellog={levellog} />
          </NoDefaultHoverLink>
        </li>
      ))}
    </ul>
  );
};

export default LevellogList;
