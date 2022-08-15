/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { Link } from 'react-router-dom';
import { PATH } from '../../constants';

import LevellogItem from '../Items/LevellogItem';

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
          <Link to={`${PATH.LEVELLOG}/${levellog.id}`}>
            <LevellogItem levellog={levellog} />
          </Link>
        </li>
      ))}
    </ul>
  );
};

export default LevellogList;
