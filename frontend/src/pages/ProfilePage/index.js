/** @jsxImportSource @emotion/react */

import StudylogOverview from './StudylogOverviews';
import Introduction from '../../components/Introduction/Introduction';
import { ProfilePageSideBar } from '../../components';

import useNotFound from '../../hooks/useNotFound';

import { Content } from './styles';
import { FlexStyle } from '../../styles/flex.styles';
import { MainContentStyle } from '../../PageRouter';
import { css } from '@emotion/react';

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
          @media screen and (max-width: 768px) {
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
            <StudylogOverview />
          </div>
        )}
      </Content>
    </div>
  );
};

export default ProfilePage;
