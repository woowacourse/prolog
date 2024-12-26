/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { ProfilePageSideBar } from '../../components';
import Introduction from '../../components/Introduction/Introduction';
import MEDIA_QUERY from '../../constants/mediaQuery';
import useNotFound from '../../hooks/useNotFound';
import { MainContentStyle } from '../../PageRouter';
import { FlexStyle } from '../../styles/flex.styles';
import StudylogOverview from './StudylogOverviews';
import { Content } from './styles';

// TODO: any 타입
const ProfilePage = ({ children, menu }) => {
  const { isNotFound, NotFound } = useNotFound();

  if (isNotFound) {
    return <NotFound />;
  }

  return (
    <div
      css={[
        MainContentStyle,
        FlexStyle,
        css`
          ${MEDIA_QUERY.md} {
            flex-direction: column;
          }
        `,
      ]}
    >
      <ProfilePageSideBar menu={menu} />
      <Content>
        {children ? (
          children
        ) : (
          <div>
            <Introduction />
          </div>
        )}
      </Content>
    </div>
  );
};

export default ProfilePage;
