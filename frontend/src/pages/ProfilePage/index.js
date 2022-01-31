/** @jsxImportSource @emotion/react */

import StudylogOverview from './StudylogOverviews';
import Introduction from '../../components/Introduction/Introduction';
import { ProfilePageSideBar } from '../../components';

import useNotFound from '../../hooks/useNotFound';

import { Content } from './styles';
import { FlexStyle } from '../../styles/flex.styles';
import { MainContentStyle } from '../../PageRouter';

const ProfilePage = ({ children, menu }) => {
  const { isNotFound, NotFound } = useNotFound();

  if (isNotFound) {
    return <NotFound />;
  }

  return (
    <div css={[MainContentStyle, FlexStyle]}>
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
