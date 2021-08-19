import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const CardInner = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;

  & > *:not(:last-child) {
    margin-bottom: 2rem;
  }

  .tui-editor-contents {
    font-size: 1.6rem;
  }
`;

const SubHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Mission = styled.div`
  font-size: 2rem;
  color: ${COLOR.DARK_GRAY_900};
  font-weight: lighter;
`;

const Title = styled.div`
  font-size: 3.6rem;
  color: ${COLOR.DARK_GRAY_900};
  font-weight: bold;
  margin-bottom: 2rem;
`;

const Tags = styled.div`
  font-size: 1.4rem;
  color: ${COLOR.LIGHT_GRAY_900};
  margin-top: auto;
`;

const IssuedDate = styled.div`
  color: ${COLOR.DARK_GRAY_800};
  font-size: 1.4rem;
`;

const ProfileChipStyle = css`
  border: none;
  padding: 0.8rem;
  cursor: pointer;

  &:hover {
    background-color: ${COLOR.LIGHT_BLUE_100};
  }
`;

export { CardInner, SubHeader, Mission, Title, Tags, IssuedDate, ProfileChipStyle };
