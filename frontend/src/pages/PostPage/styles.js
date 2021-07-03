import { css } from '@emotion/react';
import styled from '@emotion/styled';

const CardInner = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;

  & > *:not(:last-child) {
    margin-bottom: 2rem;
  }
`;

const SubHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Mission = styled.div`
  font-size: 2rem;
  color: #383838;
  font-weight: lighter;
`;

const Title = styled.div`
  font-size: 3.6rem;
  color: #383838;
  font-weight: bold;
  margin-bottom: 2rem;
`;

const Tags = styled.div`
  font-size: 1.4rem;
  color: #848484;
  margin-top: auto;
`;

const IssuedDate = styled.div`
  color: #444444;
  font-size: 1.4rem;
`;

const ProfileChipStyle = css`
  border: none;
  padding: 0;
`;

export { CardInner, SubHeader, Mission, Title, Tags, IssuedDate, ProfileChipStyle };
