/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useContext } from 'react';

import { UserContext } from '../../contexts/UserProvider';
import { useLevellogList } from '../../hooks/Levellog/useLevelLogList';

import { Button, Pagination } from '../../components';
import PencilIcon from '../../assets/images/pencil_icon.svg';

import { MainContentStyle } from '../../PageRouter';
import {
  AlignItemsCenterStyle,
  FlexStyle,
  JustifyContentEndStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import { HeaderContainer, PostListContainer } from './styles';
import LevellogList from '../../components/Lists/LevellogList';
import { Link } from 'react-router-dom';
import { PATH } from '../../constants';

const LevellogListPage = () => {
  const { levellogs, isLoading, onChangeCurrentPage } = useLevellogList();

  const { user } = useContext(UserContext);
  const { isLoggedIn } = user;

  return (
    <div css={[MainContentStyle]}>
      <HeaderContainer>
        <div
          css={[
            FlexStyle,
            JustifyContentSpaceBtwStyle,
            AlignItemsCenterStyle,
            css`
              margin-bottom: 1rem;

              @media screen and (max-width: 420px) {
                flex-direction: column;
              }
            `,
          ]}
        >
          <h1
            css={css`
              font-size: 2.4rem;
            `}
          >
            💬 레벨로그
          </h1>
        </div>
        <div
          css={[
            FlexStyle,
            JustifyContentEndStyle,
            css`
              @media screen and (max-width: 420px) {
                > button {
                  display: none;
                }
              }
            `,
          ]}
        >
          {isLoggedIn && (
            <Link to={PATH.NEW_LEVELLOG}>
              <Button
                type="button"
                size="SMALL"
                icon={PencilIcon}
                alt="글쓰기 아이콘"
                cssProps={css`
                  margin-left: 1rem;
                `}
              >
                글쓰기
              </Button>
            </Link>
          )}
        </div>
      </HeaderContainer>
      {!isLoading && (
        <>
          <PostListContainer>
            {levellogs.data.length === 0 && '작성된 글이 없습니다.'}
            {levellogs.data && <LevellogList levellogs={levellogs.data} />}
          </PostListContainer>
          <Pagination dataInfo={levellogs} onSetPage={onChangeCurrentPage}></Pagination>
        </>
      )}
    </div>
  );
};

export default LevellogListPage;
